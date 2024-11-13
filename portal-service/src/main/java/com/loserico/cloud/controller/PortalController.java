package com.loserico.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p/>
 * Copyright: Copyright (c) 2024-10-30 17:22
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/portal")
public class PortalController {
    
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/port")
    public Map<String, Integer> port() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String port = restTemplate.getForObject("http://awesome-service/aws/port", String.class);
            //System.out.println(port);
            Integer count = map.get(port);
            if (count == null) {
                map.put(port, 1);
            }else {
                map.put(port, count+1);
            }
        }
        for (int i = 0; i < 100; i++) {
            String port = restTemplate.getForObject("http://storage-service/storage/port", String.class);
            //System.out.println(port);
            Integer count = map.get(port);
            if (count == null) {
                map.put(port, 1);
            }else {
                map.put(port, count+1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
        	System.out.println(entry.getKey()+" : " + entry.getValue());
        }
        return map;
    }
}
