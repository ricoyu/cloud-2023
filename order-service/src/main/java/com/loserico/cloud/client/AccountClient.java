package com.loserico.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * Copyright: (C), 2022-09-06 13:47
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@FeignClient(name = "account-service", path = "/account")
public interface AccountClient {
	
	@RequestMapping("/debit")
	Boolean debit(@RequestParam("userId") String userId, @RequestParam("money") int money);
}
