package com.loserico.cloud.service;

import com.loserico.cloud.entity.OrderEntity;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


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
    public Long createOrder(String userId, String commodityCode, Integer count) {

        //logger.info("[createOrder] current XID: {}", RootContext.getXID());

        // deduct storage
        /*StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCommodityCode(commodityCode);
        storageDTO.setCount(count);*/
        //		Integer storageCode = storageService.reduceStock(storageDTO).getCode();
        //		if (storageCode.equals(COMMON_FAILED.getCode())) {
        //			throw new BusinessException("stock not enough");
        //		}

        // deduct balance
        /*int price = count * 2;
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setPrice(price);*/
        //		Integer accountCode = accountService.reduceBalance(accountDTO).getCode();
        //		if (accountCode.equals(COMMON_FAILED.getCode())) {
        //			throw new BusinessException("balance not enough");
        //		}
        int price = count * 2;
        // save order
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(price);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        entityOperations.persist(order);
        log.info("[createOrder] orderId: {}", order.getId());

        return order.getId();
    }
    
    public List<OrderEntity> findOrderByUserId(String userId) {
        List<OrderEntity> orders = criteriaOperations.find(OrderEntity.class, "userId", userId);
        return orders;
    }
}
