package com.piti.java.security.config;

import static com.piti.java.security.config.PermissionEnum.BRAND_READ;
import static com.piti.java.security.config.PermissionEnum.BRAND_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;;;

@Getter
public enum RoleEnum {
	ADMIN(Set.of(BRAND_READ, BRAND_WRITE)),
	SALE(Set.of(BRAND_READ));
	
	private Set<PermissionEnum> permissions;
	
	private RoleEnum(Set<PermissionEnum> permissions) {
		this.permissions = permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		Set<SimpleGrantedAuthority> permissions = this.getPermissions().stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
			.collect(Collectors.toSet());
		//
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+ this.name());
		permissions.add(role);
		//
		
		return permissions;
	}
}
