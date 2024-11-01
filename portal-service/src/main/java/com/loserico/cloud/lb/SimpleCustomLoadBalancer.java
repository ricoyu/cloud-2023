package com.loserico.cloud.lb;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 抛开Nacos基于纯SpringCloudLoaderBalancer实现一个最简单的负载均衡策略
 * <p/>
 * Copyright: Copyright (c) 2024-11-01 21:01
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public class SimpleCustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	private String serviceId;

	public SimpleCustomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
	                                String serviceId) {
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		this.serviceId = serviceId;
	}
	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		// 实现自定义的选择逻辑
		return supplier.get(request).next().map(this::chooseInstance);
	}

	private Response<ServiceInstance> chooseInstance(List<ServiceInstance> instances) {
		// 自定义的选择算法，例如：返回第一个实例, 这里ServiceInstance的实例是 NacosServiceInstance
		if (!instances.isEmpty()) {
			return new DefaultResponse(instances.get(0));
		}

		return null;
	}
}
