package com.loserico.cloud.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;

import java.util.Properties;

/**
 * @author nkorange
 */
public class NamingExample {
	
	public static void main(String[] args) throws NacosException {
		
		Properties properties = new Properties();
		properties.setProperty("serverAddr", "192.168.100.111:8848,192.168.100.112:8848,192.168.100.113:8848");
		properties.setProperty("namespace", "public");
		
		NamingService naming = NamingFactory.createNamingService(properties);
		
		naming.registerInstance("user-service", "192.168.100.106", 8080, "SZ");
		
		naming.registerInstance("user-service", "192.168.100.106", 8021, "DEFAULT");
		
		System.out.println(naming.getAllInstances("user-service"));
		
		naming.deregisterInstance("user-service", "192.168.100.106", 8080, "SZ");
		
		System.out.println(naming.getAllInstances("user-service"));
		
		naming.subscribe("user-service", new EventListener() {
			@Override
			public void onEvent(Event event) {
				System.out.println(((NamingEvent) event).getServiceName());
				System.out.println(((NamingEvent) event).getInstances());
			}
		});
	}
}
