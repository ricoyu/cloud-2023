package com.loserico.cloud.service;

import com.loserico.cloud.entity.Storage;
import com.loserico.orm.dao.EntityOperations;
import com.loserico.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StorageService {
    
    @Autowired
    private SQLOperations sqlOperations;
    
    @Autowired
    private EntityOperations entityOperations;
    
    @Transactional
    public void deduct(String commodityCode, int count){
        log.info("=============扣减库存=================");
        //log.info("当前 XID: {}", RootContext.getXID());
        // 检查库存
        checkStock(commodityCode,count);
        
        log.info("开始扣减 {} 库存", commodityCode);
        Map<String, Object> params = new HashMap<>();
        params.put("commodityCode", commodityCode);
        params.put("count", count);
        Integer record = sqlOperations.executeUpdate("reduceStorage", params);
        log.info("扣减 {} 库存结果:{}", commodityCode, record > 0 ? "操作成功" : "扣减库存失败");
    }
    
    private void checkStock(String commodityCode, int count){
        
        log.info("检查 {} 库存", commodityCode);
        Storage storage = sqlOperations.query4One("findByCommodityCode", "commodityCode", commodityCode, Storage.class);
        
        if (storage.getCount() < count) {
            log.warn("{} 库存不足，当前库存:{}", commodityCode, count);
            throw new RuntimeException("库存不足");
        }
        
    }
	
	public List<Storage> list() {
        return entityOperations.findAll(Storage.class);
	}
}
