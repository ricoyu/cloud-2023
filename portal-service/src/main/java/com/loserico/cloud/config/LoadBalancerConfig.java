package com.loserico.cloud.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class LoadBalancerConfig {

	public static class StorageLBConfig {

		@Bean
		public ReactorServiceInstanceLoadBalancer storageLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplier) {
			return new RoundRobinLoadBalancer(serviceInstanceListSupplier, "storage-service");
		}
	}

	public static class AwesomeLBConfig {

		@Bean
		public ReactorServiceInstanceLoadBalancer awesomeLoadBalancer(Environment environment,
		                                                                        ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplier) {
			String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
			return new RandomLoadBalancer(serviceInstanceListSupplier, serviceId);
		}
	}

	public static class DefaultLoadBalancerConfiguration {

		@Bean
		public ReactorServiceInstanceLoadBalancer randomLoadBalancer(Environment environment,
		                                                        ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplier,
		                                                        NacosDiscoveryProperties nacosDiscoveryProperties) {
			String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
			return new NacosLoadBalancer(serviceInstanceListSupplier, serviceId, nacosDiscoveryProperties);
		}

	}
}

