package com.loserico.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.loserico.cloud.entity.OrderEntity;
import com.loserico.cloud.service.OrderService;
import com.loserico.common.lang.errors.ErrorTypes;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
public class OrderControllerV2 {

	@Autowired
	private OrderService orderService;

	@PostConstruct
	public void init() {
		List<FlowRule> flowRules = new ArrayList<>();
		//创建流控规则对象
		FlowRule flowRule = new FlowRule();
		//设置流控规则 QPS
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		//设置受保护的资源, 下面SphU.entry进入的是这边设置的resource
		flowRule.setResource("helloSentinelV2"); //是自己给要保护的资源起的一个名字
		flowRule.setCount(1);//设置受保护的资源的阈值
		flowRules.add(flowRule);

		//加载配置好的规则
		FlowRuleManager.loadRules(flowRules);
	}

	@GetMapping("/v2/findOrderByUserId/{userId}")
	@SentinelResource(value = "helloSentinelV2", blockHandler = "blockHandlerForHelloSentinelV2")
	public Result helloSentinelV2(@PathVariable String userId) {
		List<OrderEntity> orders = orderService.findOrderByUserId(userId);
		return Results.success().result(orders);
	}

	public Result blockHandlerForHelloSentinelV2(String userId, BlockException e) {
		log.error("", e);
		return Results.status(ErrorTypes.FLOW_EXCEPTION).build();
	}
}
