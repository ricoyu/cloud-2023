package com.loserico.cloud.service;

import com.loserico.common.lang.exception.BusinessException;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Fox
 */
@Service
@Slf4j
public class AccountService{
    
    @Autowired
    private SQLOperations sqlOperations;
    
    @Autowired
    private EntityOperations entityOperations;

    /**
     * 扣减账余额
     * @param userId
     * @param price
     * @throws BusinessException
     */
    @Transactional
    public void reduceBalance(String userId, Integer price) throws BusinessException {
        //logger.info("[reduceBalance] currenet XID: {}", RootContext.getXID());

        checkBalance(userId, price);

        LocalDateTime updateTime = LocalDateTime.now();
        Map<String, Object> params = new HashMap<>();
        params.put("price", price);
        params.put("userId", userId);
        params.put("updateTime", updateTime);
        int updateCount =
                sqlOperations.executeUpdate("UPDATE account SET money = money - :price,update_time = :updateTime WHERE user_id = :userId AND money >= :price",
                        params);
        if (updateCount == 0) {
            throw new BusinessException("reduce balance failed");
        }
    }

    /**
     * 获取账户余额
     * @param userId
     * @return
     */
    public Integer getRemainAccount(String userId) {
        //return sqlOperations.query4One("SELECT money FROM account WHERE user_id = :userId", "userId", userId);
        return sqlOperations.query4One("selectByUserId", "userId", userId);
    }

    private void checkBalance(String userId, Integer price) throws BusinessException {
        Integer balance = sqlOperations.query4One("SELECT money FROM account WHERE user_id = :userId", "userId", userId);
        if (balance < price) {
            throw new BusinessException("no enough balance");
        }
    }
}
