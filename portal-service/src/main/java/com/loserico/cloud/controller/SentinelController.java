package com.loserico.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 专门用来测试Sentinel Dashboard的Controller
 * <p/>
 * Copyright: Copyright (c) 2024-12-22 19:53
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

	private AtomicInteger counter = new AtomicInteger(0);

	private long lastRequestTime;

	@SentinelResource(value = "/sentinel/orders", blockHandler = "handleHotParam")
	@PostMapping("/orders")
	public Result hotParam(Integer userId) {
		return Results.success().result(userId);
	}

	public Result handleHotParam(Integer userId, BlockException e) {
		return Results.success().result("还没有订单");
	}

	@PostMapping("/counter-reset")
	public Result resetCounter() {
		counter.set(0);
		lastRequestTime = 0;
		return Results.success().build();
	}

	@GetMapping("/direct-flow")
	@SentinelResource(value = "direct-flow", blockHandler = "handleDirectFlow")
	public Result directFlow() {
		log.info("====== 第 {} 次调用, 与上次请求相差 {} 毫秒======", counter.incrementAndGet(), (System.currentTimeMillis()-lastRequestTime));
		lastRequestTime = System.currentTimeMillis();
		return Results.success().build();
	}

	public Result handleDirectFlow(BlockException e) {
		return Results.success().result("被直接流控了");
	}
}
