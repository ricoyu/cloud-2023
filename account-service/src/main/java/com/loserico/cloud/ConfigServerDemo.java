package com.loserico.cloud;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;

import static java.util.concurrent.TimeUnit.*;

/**
 * <p>
 * Copyright: (C), 2022-08-14 16:58
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class ConfigServerDemo {
	
	public static void main(String[] args) throws NacosException {
		String serverAddr = "localhost:8848";
		String dataId = "user-service.yaml";
		String group = "DEFAULT_GROUP";
		
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
		
		//获取配置中心服务
		ConfigService configService = NacosFactory.createConfigService(properties);
		//从配置中心拉取配置
		String config = configService.getConfig(dataId, group, 5000);
		System.out.println(config);
		//注册监听器
		configService.addListener(dataId, group, new Listener() {
			
			@Override
			public Executor getExecutor() {
				return null;
			}
			
			@Override
			public void receiveConfigInfo(String configInfo) {
				System.out.println("感知配置变化:" + configInfo);
			}
		});
		
		//发布配置
		configService.publishConfig(dataId, group, "common.age=30", ConfigType.PROPERTIES.getType());
		try {
			SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
