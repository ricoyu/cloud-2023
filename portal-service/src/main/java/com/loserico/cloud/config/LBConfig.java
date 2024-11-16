package com.loserico.cloud.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClients(
		//value = {
		//		@LoadBalancerClient(name = "awesome-service", configuration = LoadBalancerConfig.AwesomeLBConfig.class),
		//		@LoadBalancerClient(name = "storage-service", configuration = LoadBalancerConfig.StorageLBConfig.class)
		//}
		defaultConfiguration = LoadBalancerConfig.DefaultLoadBalancerConfiguration.class
)
public class LBConfig {
}
