package com.loserico.cloud.controller;

import com.loserico.common.lang.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@RestController
@RequestMapping("/aws")
public class AwesomeController {

	private boolean isRetry = false;

	private String idempotentMarked;
	AtomicInteger counter = new AtomicInteger(1);

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

	@GetMapping("/retry")
	public String retry(@RequestHeader("Idempotent") String idempotent) {
		if (idempotentMarked != null && !idempotentMarked.equals(idempotent)) {
			counter.set(1);
		}
		idempotentMarked = idempotent;
		System.out.println("第" + counter.getAndIncrement() + "调用了retry");
		try {
			MILLISECONDS.sleep(3000);
		} catch (InterruptedException e) {
			log.info("超时");
		}
		return "ok";
	}

	@GetMapping("/compression")
	public String compression() {
		String readme = IOUtils.readFileAsString("D:\\Learning\\cloud-2023\\readme.md");
		System.out.println("字节数: " + readme.getBytes(UTF_8).length);
		return readme;
	}
}
