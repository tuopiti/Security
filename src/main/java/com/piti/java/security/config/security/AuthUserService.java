package com.piti.java.security.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.piti.java.security.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService{
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		return userService.loadUserByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User [%s] is not found".formatted(username)));
	}

}
