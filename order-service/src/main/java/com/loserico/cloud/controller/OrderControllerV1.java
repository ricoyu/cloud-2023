package com.loserico.cloud.controller;

import com.loserico.cloud.entity.OrderEntity;
import com.loserico.cloud.service.OrderService;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Copyright: (C), 2022-07-23 9:11
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderControllerV1 {

	@Autowired
	private OrderService orderService;

	@GetMapping("/v1/findOrderByUserId/{userId}")
	public Result findOrderByUserId(@PathVariable String userId) {
		List<OrderEntity> orders = orderService.findOrderByUserId(userId);
		return Results.success().result(orders);
	}

}
