package com.loserico.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@RestController
@RequestMapping("/aws")
public class AwesomeController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/port")
	public String port(@RequestHeader(value = "Idempotent", required = false) String header) {
		System.out.println("Idempotent header: " + header);
		return environment.getProperty("local.server.port");
	}

	@GetMapping("/timeout")
	public String timeout() {
		try {
			SECONDS.sleep(2);
		} catch (InterruptedException e) {
			log.error("", e);
		}
		return environment.getProperty("local.server.port");
	}
}
