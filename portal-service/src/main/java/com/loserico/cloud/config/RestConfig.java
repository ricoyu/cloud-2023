package com.loserico.cloud.config;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * Copyright: (C), 2022-07-23 9:10
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class RestConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		String json = """
				{
				  "app": "portal-service",
				  "gmtCreate": null,
				  "gmtModified": null,
				  "id": 1,
				  "ip": null,
				  "port": null,
				  "rule": {
				    "burstCount": 0,
				    "clusterConfig": {
				      "fallbackToLocalWhenFail": true,
				      "flowId": null,
				      "sampleCount": 10,
				      "thresholdType": 0,
				      "windowIntervalMs": 1000
				    },
				    "clusterMode": false,
				    "controlBehavior": 0,
				    "count": 1.0,
				    "durationInSec": 2,
				    "grade": 1,
				    "id": null,
				    "limitApp": "default",
				    "maxQueueingTimeMs": 0,
				    "paramFlowItemList": [],
				    "paramIdx": 0,
				    "resource": "/sentinel/orders"
				  }
				}""";

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ParamFlowRule paramFlowRule = objectMapper.readValue(json, ParamFlowRule.class);
			System.out.println(paramFlowRule.getCount());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
