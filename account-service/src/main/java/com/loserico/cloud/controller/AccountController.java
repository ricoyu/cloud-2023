package com.loserico.cloud.controller;

import com.loserico.cloud.dto.AccountDTO;
import com.loserico.cloud.service.AccountService;
import com.loserico.common.lang.exception.BusinessException;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/")
	public Result getRemainAccount(@RequestParam("userId") String userId) {
		Integer remainAccount = accountService.getRemainAccount(userId);
		return Results.success().result(remainAccount);
	}

	@PostMapping("/reduce-balance")
	public Result reduceBalance(@RequestBody AccountDTO accountDTO) {
		try {
			accountService.reduceBalance(accountDTO.getUserId(), accountDTO.getPrice());
		}
		catch (BusinessException e) {
			return Results.fail()
					.message(e.getMessage())
					.build();
		}
		return Results.success().build();
	}
}

