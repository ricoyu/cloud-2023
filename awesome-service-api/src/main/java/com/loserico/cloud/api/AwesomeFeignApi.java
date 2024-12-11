package com.loserico.cloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "awesome-service", contextId = "awesome-api", path = "/aws")
public interface AwesomeFeignApi {

	@GetMapping("/port")
	public String port();

	@GetMapping("/timeout")
	public String timeout();

	@GetMapping("/retry")
	public boolean retry(@RequestHeader("Idempotent") String idempotent);

	@GetMapping("/compression")
	public String compression();
}
