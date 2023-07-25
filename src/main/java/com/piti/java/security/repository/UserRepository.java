package com.piti.java.security.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.piti.java.security.model.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE users u SET is_enabled = true WHERE username = :username", nativeQuery=true)
	int enableUser(@Param("username") String username);
}
