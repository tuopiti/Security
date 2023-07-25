package com.piti.java.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piti.java.security.config.RoleEnum;
import com.piti.java.security.config.security.AuthUser;
import com.piti.java.security.model.User;
import com.piti.java.security.service.UserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FakeUserServiceImpl implements UserService{
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<AuthUser> loadUserByUsername(String username) {
		return getAuthUsers().stream()
			.filter(user -> user.getUsername().equals(username))
			.findFirst();
	}
	
	private List<AuthUser> getAuthUsers(){
		AuthUser dara = new AuthUser("mesa", passwordEncoder.encode("mesa"),RoleEnum.SALE.getAuthorities(),  true, true, true, true);
		AuthUser thida = new AuthUser("thida", passwordEncoder.encode("thida"),RoleEnum.ADMIN.getAuthorities(),  true, true, true, true);
		return List.of(dara, thida);
	}

	@Override
	public String signUpUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String confirmTokenEmail(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
