package com.loserico.cloud.controller;

import com.loserico.cloud.api.AwesomeFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
@RestController
@RequestMapping("/portal")
public class PortalController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AwesomeFeignApi awesomeApi;

    @GetMapping("/info")
    public String info() {
        return "name: " + name + ", age: " + age;
    }

    @GetMapping("/port")
    public Map<String, Integer> port() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String port = awesomeApi.port();
            //System.out.println(port);
            Integer count = map.get(port);
            if (count == null) {
                map.put(port, 1);
            }else {
                map.put(port, count+1);
            }
        }
        return map;
    }

    @GetMapping("/timeout-port")
    public Map<String, Integer> timeout() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String port = awesomeApi.timeout();
            //System.out.println(port);
            Integer count = map.get(port);
            if (count == null) {
                map.put(port, 1);
            }else {
                map.put(port, count+1);
            }
        }
        return map;
    }

    @Autowired
    private AwesomeFeignApi awesomeFeignApi;

    @GetMapping("/retry")
    public boolean retry() {
        return awesomeFeignApi.retry(null);
    }
}
