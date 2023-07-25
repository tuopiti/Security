package com.piti.java.security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.piti.java.security.config.PermissionEnum;

import static org.springframework.security.config.Customizer.withDefaults;
import com.piti.java.security.config.security.jwt.JwtLoginFilter;
import com.piti.java.security.config.security.jwt.TokenVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                //.csrf(withDefaults())
                .csrf()
                .disable()
                .addFilter(new JwtLoginFilter(authenticationManager2(authenticationConfiguration)))
                .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests()
                .requestMatchers("/api/v*/registration/**").permitAll()
                //.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                //.requestMatchers(HttpMethod.GET, "/api/v1/sale/**")
                //.hasAuthority(PermissionEnum.BRAND_READ.getDescription())
                //.requestMatchers(HttpMethod.POST, "/api/v1/sale/**")
                //.hasAuthority(PermissionEnum.BRAND_WRITE.getDescription())
                .anyRequest()
                .authenticated();
		    //.and()
		    //.httpBasic();
		return http.build();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);       
        return provider;
    }
	
	@Bean
	AuthenticationManager authenticationManager2(AuthenticationConfiguration authenticationConfiguration) 
	    throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	/*
	@Bean
	protected UserDetailsService userDetailsService() {
		//User dara = new User("dara", passwordEncoder.encode("dara123"), Collections.emptyList());
		
		UserDetails dara = User.builder()
			.username("dara")
			.password(passwordEncoder.encode("dara123"))
			//.roles("SALE") // ROLE_SALE
			.authorities(RoleEnum.SALE.getAuthorities())
			.build();
			
		UserDetails thida = User.builder()
			.username("thida")
			.password(passwordEncoder.encode("thida"))
			//.roles("ADMIN") // ROLE_ADMIN
			.authorities(RoleEnum.ADMIN.getAuthorities())
			.build();
		
		UserDetailsService userDetailsService = new InMemoryUserDetailsManager(dara,thida);
		return userDetailsService;
	}
	*/
}
