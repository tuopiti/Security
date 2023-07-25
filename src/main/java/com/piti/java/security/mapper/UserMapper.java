package com.piti.java.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.piti.java.security.config.security.AuthUser;
import com.piti.java.security.model.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	AuthUser toAuthUser(User user);
}
