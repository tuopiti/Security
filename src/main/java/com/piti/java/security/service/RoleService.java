package com.piti.java.security.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.piti.java.security.model.Role;
import com.piti.java.security.model.User;

public interface RoleService {
	
	List<Role> findAll();
	
	Optional<Role> findById(Long id);
	
	void delete(Long id);
	
	void save(Role role);
	
	void assignUserRole(Long userId, Long roleId);
	
	void unassignUserRole(Long userId, Long roleId);
	
	Set<Role> getUserRoles(User user);
	
	List<Role> getUserNotRoles(User user);
}
