package com.loserico.cloud.service;

import com.loserico.cloud.entity.OrderEntity;
import com.loserico.cloud.entity.OrderStatus;
import com.loserico.cloud.vo.OrderVo;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Fox
 */
@Service
@Slf4j
public class OrderService {
    
    //@Autowired
    //private AccountClient accountClient;
    
    //@Autowired
    //private StorageClient storageClient;
    
    @Autowired
    private EntityOperations entityOperations;
    
    @Autowired
    private SQLOperations sqlOperations;
    
    @Autowired
    private CriteriaOperations criteriaOperations;
    
    @Transactional
    //@GlobalTransactional(name="createOrder",rollbackFor=Exception.class)
    public OrderEntity saveOrder(OrderVo orderVo) {
        log.info("=============用户下单=================");
        //log.info("当前 XID: {}", RootContext.getXID());
        
        // 保存订单
        OrderEntity order = new OrderEntity();
        order.setUserId(orderVo.getUserId());
        order.setCommodityCode(orderVo.getCommodityCode());
        order.setCount(orderVo.getCount());
        order.setMoney(orderVo.getMoney());
        order.setStatus(OrderStatus.INIT.getValue());
    
       entityOperations.persist(order);
        log.info("保存订单{}", "成功");
        
        //扣减库存
        //storageClient.deduct(orderVo.getCommodityCode(), orderVo.getCount());
        
        //扣减余额
        //Boolean debit= accountClient.debit(orderVo.getUserId(), orderVo.getMoney());
        
        //更新订单
        Map<String, Object> params = new HashMap<>();
        params.put("status", OrderStatus.SUCCESS.getValue());
        params.put("id", order.getId());
        Integer updateOrderRecord = sqlOperations.executeUpdate("updateOrderStatus", params);
        log.info("更新订单id:{} {}", order.getId(), updateOrderRecord > 0 ? "成功" : "失败");
        
        return order;
        
    }
    
    public List<OrderEntity> findOrderByUserId(String userId) {
        List<OrderEntity> orders = criteriaOperations.find(OrderEntity.class, "userId", userId);
        return orders;
    }
}
