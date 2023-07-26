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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		return userService.loadUserByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User [%s] is not found".formatted(email)));
	}

}
