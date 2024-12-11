package com.loserico.cloud.storage;

import com.loserico.cloud.dto.StorageDTO;
import com.loserico.common.lang.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service",contextId = "storage-api", path = "/storage")
public interface StorageFeignApi {


	/**
	 * 扣减库存
	 *
	 * @param storageDTO
	 * @return
	 */
	@PostMapping("/reduce-stock")
	public Result reduceStock(@RequestBody StorageDTO storageDTO);

	/**
	 * 获取库存余额
	 * @param commodityCode
	 * @return
	 */
	@GetMapping("/")
	public Result getRemainCount(@RequestParam("commodityCode") String commodityCode);
}
