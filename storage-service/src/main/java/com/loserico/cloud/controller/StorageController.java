package com.loserico.cloud.controller;

import com.loserico.cloud.entity.Storage;
import com.loserico.cloud.service.StorageService;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {


    @Autowired
    private Environment environment;

    @Autowired
    private StorageService storageService;


    @GetMapping("/port")
    public String port() {
        return environment.getProperty("local.server.port");
    }

    @RequestMapping(path = "/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        // 扣减库存
        storageService.deduct(commodityCode, count);
        return true;
    }
    
    @GetMapping("/list")
    public Result listStorages() {
        List<Storage> storages = storageService.list();
        return Results.success().result(storages);
    }
}
