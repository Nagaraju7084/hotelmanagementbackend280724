package com.hotel.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

//creating web security config and cors filter
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(request ->
		request.antMatchers("/api/auth/**").permitAll()).build();
		//later we will define the roles and customer api
	}
}
