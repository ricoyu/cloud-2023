package com.loserico.cloud.service;

import com.loserico.cloud.account.AccountFeignApi;
import com.loserico.cloud.dto.AccountDTO;
import com.loserico.cloud.dto.StorageDTO;
import com.loserico.cloud.entity.OrderEntity;
import com.loserico.cloud.storage.StorageFeignApi;
import com.loserico.common.lang.exception.BusinessException;
import com.loserico.common.lang.vo.Result;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Fox
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private AccountFeignApi accountFeignApi;

    @Autowired
    private StorageFeignApi storageFeignApi;

    @Autowired
    private RestTemplate restTemplate;
    
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
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCommodityCode(commodityCode);
        storageDTO.setCount(count);

        Result result = storageFeignApi.reduceStock(storageDTO);
        // deduct balance
        int price = count * 2;
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setPrice(price);
        Result accountResult = accountFeignApi.reduceBalance(accountDTO);
        if (!accountResult.isSuccess()) {
            throw new BusinessException(accountResult.getCode(), accountResult.getMessageStr());
        }
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
