package com.hotel.backend.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.backend.dto.SignupRequest;
import com.hotel.backend.dto.UserDto;
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
	
	public UserDto createUser(SignupRequest signupRequest) {
		if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
			throw new EntityExistsException("user already present with the email " + signupRequest.getEmail());
		}
		
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setUserRole(UserRole.CUSTOMER);
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		User createdUser = userRepository.save(user);
		return createdUser.getUserDto();
	}
}
