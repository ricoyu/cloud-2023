package com.loserico.cloud.config;

import com.loserico.cloud.lb.SimpleCustomLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClients({
		@LoadBalancerClient(name = "storage-service", configuration = LoadBalancerConfiguration.StorageServiceLoadBalancerConfig.class),
		@LoadBalancerClient(name = "awesome-service", configuration = LoadBalancerConfiguration.AwesomeServiceLoadBalancerConfig.class)
})
public class LoadBalancerConfiguration {

	public class StorageServiceLoadBalancerConfig {

		@Bean
		public RoundRobinLoadBalancer roundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> supplier) {
			return new RoundRobinLoadBalancer(supplier, "storage-service1");
		}
	}

    public class AwesomeServiceLoadBalancerConfig {

		@Bean
		public ReactorServiceInstanceLoadBalancer randomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> supplier) {
			return new SimpleCustomLoadBalancer(supplier, "awesome-service");
		}
	}
}

