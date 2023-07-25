package com.piti.java.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.security.model.User;
import com.piti.java.security.service.RoleService;
import com.piti.java.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final RoleService roleService;
	
	@PostMapping
    public String register(@RequestBody User user) {
        return userService.signUpUser(user);
    }
	
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {		
		return userService.confirmTokenEmail(token);
		
	}
	
	@PostMapping("/role/assign/{userId}/{roleId}")
	public ResponseEntity<?> assignRole(@PathVariable Long userId, 
	                         @PathVariable Long roleId){
	    roleService.assignUserRole(userId, roleId);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/role/unassign/{userId}/{roleId}")
	public ResponseEntity<?> unassignRole(@PathVariable Long userId,
	                           @PathVariable Long roleId){
	    roleService.unassignUserRole(userId, roleId);
	    return ResponseEntity.ok().build();
	}
}
