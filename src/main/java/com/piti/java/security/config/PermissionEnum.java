package com.piti.java.security.config;

import lombok.Getter;

@Getter
public enum PermissionEnum {
	BRAND_READ("brand:read"), BRAND_WRITE("brand:write");
	private String description;
	
	private PermissionEnum(String description) {
		this.description = description;
	}
}
