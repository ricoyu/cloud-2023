package com.loserico.cloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "awesome-service", path = "/aws")
public interface AwesomeApi {

	@GetMapping("/port")
	public String port();

	@GetMapping("/timeout")
	public String timeout();
}
