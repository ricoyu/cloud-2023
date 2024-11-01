package com.loserico.cloud.controller;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.loserico.cloud.entity.Account;
import com.loserico.cloud.service.AccountService;
import com.loserico.common.lang.vo.Result;
import com.loserico.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/info/{id}")
	public Result accountInfo(@PathVariable Integer id) {
		Account account = accountService.getById(id);
		//try {
		//	SECONDS.sleep(2);
		//} catch (InterruptedException e) {
		//	throw new RuntimeException(e);
		//}
		return Results.success().result(account);
	}
	
	@PostMapping("/notify")
	public String notifySlow(@RequestBody Object object) {
		log.info("收到Skywalking的告警通知: {}", JacksonUtils.toJson(object));
		return "Notify success";
	}
}

