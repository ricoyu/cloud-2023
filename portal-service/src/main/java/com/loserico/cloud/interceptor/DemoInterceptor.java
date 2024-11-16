package com.loserico.cloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.UUID;

public class DemoInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		if (!template.headers().containsKey("Idempotent")) {
			template.header("Idempotent", UUID.randomUUID().toString().replaceAll("-", ""));
		}
	}
}
