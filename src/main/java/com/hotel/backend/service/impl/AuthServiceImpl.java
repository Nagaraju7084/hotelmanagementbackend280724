package com.hotel.backend.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.backend.entity.User;
import com.hotel.backend.repository.UserRepository;
import com.hotel.backend.role.UserRole;
import com.hotel.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	
	@PostConstruct
	public void createAnAdminAccount() {
		
		Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		
		if(!adminAccount.isPresent()) {
			User user =new User();
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setUserRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepository.save(user);
			
			System.out.println("admin account created successfully...");
		}else {
			System.out.println("admin account already exists...");
		}
	}
}
