package com.piti.java.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.piti.java.security.model.Role;
import com.piti.java.security.model.User;
import com.piti.java.security.repository.RoleRepository;
import com.piti.java.security.repository.UserRepository;
import com.piti.java.security.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Override
	public List<Role> findAll() {		
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(Long id) {		
		return roleRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		roleRepository.deleteById(id);
		
	}

	@Override
	public void save(Role role) {
		roleRepository.save(role);
		
	}

	@Override
	public void assignUserRole(Long userId, Long roleId) {
	    User user  = userRepository.findById(userId).orElse(null);
		Role role = roleRepository.findById(roleId).orElse(null);
	    Set<Role> userRoles = user.getRoles();
	    userRoles.add(role);
	    user.setRoles(userRoles);
	    userRepository.save(user);
		
	}

	@Override
	public void unassignUserRole(Long userId, Long roleId) {
		 User user  = userRepository.findById(userId).orElse(null);
		 user.getRoles().removeIf(x -> x.getId()==roleId);
		 userRepository.save(user);		
	}

	@Override
	public Set<Role> getUserRoles(User user) {
		return user.getRoles();
	}

	@Override
	public List<Role> getUserNotRoles(User user) {
		return roleRepository.getUserNotRoles(user.getId());
	}

}
