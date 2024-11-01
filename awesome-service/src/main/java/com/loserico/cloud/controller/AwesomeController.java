package com.loserico.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/aws")
public class AwesomeController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/port")
	public String port() {
		return environment.getProperty("local.server.port");
	}
}
