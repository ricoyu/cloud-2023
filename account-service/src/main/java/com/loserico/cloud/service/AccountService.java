package com.loserico.cloud.service;

import com.loserico.cloud.entity.Account;
import com.loserico.common.lang.exception.BusinessException;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Fox
 */
@Service
@Slf4j
public class AccountService{
    
    private static final String ERROR_USER_ID = "1002";
    
    @Autowired
    private SQLOperations sqlOperations;
    
    @Autowired
    private EntityOperations entityOperations;
    
    /**
     * 扣减用户金额
     * @param userId
     * @param money
     */

    private void checkBalance(String userId, int money){
        log.info("检查用户 {} 余额", userId);
        Account account = sqlOperations.query4One("selectByUserId", "userId", userId, Account.class);
        
        if (account.getMoney() < money) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, account.getMoney());
            throw new BusinessException("40001", "余额不足");
        }
        
    }
    
    public Account getById(Integer id) {
        return entityOperations.get(Account.class, id);
    }
}
