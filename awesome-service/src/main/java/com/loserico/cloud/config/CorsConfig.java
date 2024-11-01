package com.loserico.cloud.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {

	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // 可以调整为具体的路径
						.allowedOriginPatterns("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}*/
}
