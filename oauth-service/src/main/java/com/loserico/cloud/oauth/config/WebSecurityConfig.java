package com.loserico.cloud.oauth.config;

import com.loserico.cloud.oauth.service.JdbcUserDetailsService;
import com.loserico.oauth2.endpoint.Oauth2AuthenticationentryPoint;
import com.loserico.oauth2.handler.LoginFailureHandler;
import com.loserico.security.filter.SecurityExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * <p/>
 * Copyright: Copyright (c) 2025-01-11 20:34
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(form -> form
						.failureHandler(loginFailureHandler())
				)
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.anyRequest().authenticated()
				)
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(oauth2AuthenticationentryPoint()));
		return http.build();
	}

	@Bean
	public Oauth2AuthenticationentryPoint oauth2AuthenticationentryPoint() {
		return new Oauth2AuthenticationentryPoint();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(JdbcUserDetailsService jdbcUserDetailsService, PasswordEncoder passwordEncoder) {
		// 创建 DaoAuthenticationProvider，将 UserDetailsService 和 PasswordEncoder 配置到 AuthenticationManager
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(jdbcUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public JdbcUserDetailsService jdbcUserDetailsService() {
		return new JdbcUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityExceptionFilter exceptionFilter() {
		return new SecurityExceptionFilter();
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler();
	}

}
