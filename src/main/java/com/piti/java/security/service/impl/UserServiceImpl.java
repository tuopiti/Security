package com.piti.java.security.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piti.java.security.config.security.AuthUser;
import com.piti.java.security.exception.ApiException;
import com.piti.java.security.mapper.UserMapper;
import com.piti.java.security.model.ConfirmationTokenEmail;
import com.piti.java.security.model.Role;
import com.piti.java.security.model.User;
import com.piti.java.security.repository.ConfirmationTokenEmailRepository;
import com.piti.java.security.repository.RoleRepository;
import com.piti.java.security.repository.UserRepository;
import com.piti.java.security.service.UserService;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenEmailRepository confirmationTokenEmailRepository;
	
	@Override
	public Optional<AuthUser> loadUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User not found %s".formatted(email)));
	    AuthUser authUser = UserMapper.INSTANCE.toAuthUser(user);
	    
	    //authUser.setGrantedAuthorities(user.getRole().getAuthorities());
	    //@TODO Update Role
	    Set<SimpleGrantedAuthority> authorities = user.getRoles()
			.stream()
			.flatMap(role -> toStreamOfSimpleGrantedAuthority(role))
			.collect(Collectors.toSet());
		authUser.setGrantedAuthorities(authorities);
	    return Optional.ofNullable(authUser);
	}
	
	private Stream<SimpleGrantedAuthority> toStreamOfSimpleGrantedAuthority(Role role){
		Set<SimpleGrantedAuthority> permissions = role.getPermissions().stream()
				.map(p -> new SimpleGrantedAuthority(p.getName()))
				.collect(Collectors.toSet());
			permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			return permissions.stream();
	}

	@Override
	public String signUpUser(User user) {		
		
		//boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
		
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
				             
		if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }
		
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		
		Role role = roleRepository.findByName("ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Set.of(role));        
		userRepository.save(user);
		
		
		String token = UUID.randomUUID().toString();
		ConfirmationTokenEmail confirmationToken = new ConfirmationTokenEmail(
				token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
		
		//confirmationTokenService.saveConfirmationToken(confirmationToken);
		confirmationTokenEmailRepository.save(confirmationToken);
		String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
		
		
		 
	    return token;
	}

	@Override
	public String confirmTokenEmail(String token) {
		ConfirmationTokenEmail confirmationToken = confirmationTokenEmailRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
		 
		 if (confirmationToken.getConfirmedAt() != null) {
	         throw new IllegalStateException("email already confirmed");
	     }
		 
		 LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        //userRepository.enableUser(confirmationToken.getUser().getUsername()); 
        userRepository.enableUser(confirmationToken.getUser().getEmail()); 
        confirmationTokenEmailRepository.updateConfirmedAt(token);
        return "confirmed";
	}
	
	
	private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }

}
