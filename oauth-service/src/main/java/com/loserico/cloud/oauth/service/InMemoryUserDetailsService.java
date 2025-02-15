package com.loserico.cloud.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 任何用户过来, 都构建一个UserDetails, 它的用户名是传入的用户名, 密码写死123456
 * <p/>
 * Copyright: Copyright (c) 2025-01-11 20:33
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@Component
public class InMemoryUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("当前登陆用户名为:{}", username);
		return User.builder().username(username)
				.password(passwordEncoder.encode("123456"))
				.authorities("ROLE_ADMIN")
				.build();
	}
}
