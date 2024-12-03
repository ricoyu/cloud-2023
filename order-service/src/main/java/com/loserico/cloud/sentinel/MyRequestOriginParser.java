package com.loserico.cloud.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Copyright: (C), 2022-08-21 10:22
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Component
public class MyRequestOriginParser implements RequestOriginParser {
	@Override
	public String parseOrigin(HttpServletRequest request) {
		return request.getParameter("serviceName");
	}
}
