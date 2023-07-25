package com.piti.java.security.service;

import java.util.Optional;

import com.piti.java.security.config.security.AuthUser;
import com.piti.java.security.model.User;

public interface UserService {
	Optional<AuthUser> loadUserByUsername(String username);
	
	String signUpUser(User user);
	
	String confirmTokenEmail(String token);
}
