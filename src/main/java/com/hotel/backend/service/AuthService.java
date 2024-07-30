package com.hotel.backend.service;

import com.hotel.backend.dto.SignupRequest;
import com.hotel.backend.dto.UserDto;

public interface AuthService {
	UserDto createUser(SignupRequest signupRequest);
}
