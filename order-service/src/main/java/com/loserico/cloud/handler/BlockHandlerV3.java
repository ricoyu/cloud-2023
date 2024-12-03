package com.loserico.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.loserico.common.lang.errors.ErrorTypes;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockHandlerV3 {


	public static Result blockHandlerForHelloSentinelV3(String userId, BlockException e) {
		log.error("", e);
		return Results.status(ErrorTypes.FLOW_EXCEPTION).build();
	}
}
