package com.loserico.cloud.config;

import com.loserico.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Copyright: (C), 2022-07-23 9:55
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class JpaConfig {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
