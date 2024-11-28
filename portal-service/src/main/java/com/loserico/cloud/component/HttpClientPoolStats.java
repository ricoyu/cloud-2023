package com.loserico.cloud.component;

import com.loserico.common.lang.utils.ReflectionUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpClientPoolStats {

	@Autowired
	private HttpClient httpClient;  // 确保这是一个配置了连接池的HttpClient实例

	public void printPoolStats() {
		PoolingHttpClientConnectionManager connManager =
				(PoolingHttpClientConnectionManager) ReflectionUtils.getFieldValue("connManager", httpClient);
		System.out.print("HttpClient Pool Stats:");
		PoolStats stats = connManager.getTotalStats();
		int maxTotal = connManager.getMaxTotal();
		System.out.println("maxTotal: " + maxTotal);
		System.out.println("Max: " + stats.getMax());
		System.out.println("Available: " + stats.getAvailable());
		System.out.println("Leased: " + stats.getLeased());
		System.out.println("Pending: " + stats.getPending());
		System.out.println("--------------------------------------");
	}
}