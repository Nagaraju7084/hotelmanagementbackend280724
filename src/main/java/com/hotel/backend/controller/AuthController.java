package com.hotel.backend.controller;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.backend.dto.SignupRequest;
import com.hotel.backend.dto.UserDto;
import com.hotel.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		try {
			UserDto createdUser = authService.createUser(signupRequest);
			return new ResponseEntity<>(createdUser, HttpStatus.OK);
		}catch(EntityExistsException entityExistsException) {
			return new ResponseEntity<>("user already exists", HttpStatus.NOT_ACCEPTABLE);
		}catch(Exception e) {
			return new ResponseEntity<>("user not created try again later!", HttpStatus.BAD_REQUEST);
		}
	}
}
