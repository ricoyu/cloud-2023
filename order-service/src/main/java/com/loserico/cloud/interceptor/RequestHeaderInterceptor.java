package com.loserico.cloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * <p>
 * Copyright: (C), 2022-07-27 9:46
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class RequestHeaderInterceptor implements RequestInterceptor {
	
	@Override
	public void apply(RequestTemplate template) {
		template.header("Auth", "Bear: asdkjajkhsdkjahsdkah");
	}
}
