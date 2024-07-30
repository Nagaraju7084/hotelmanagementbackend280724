package com.hotel.backend.dto;

import com.hotel.backend.role.UserRole;

import lombok.Data;

//after successful signup of the user, need to send the user details
//we will use this dto in other apis as well
@Data
public class UserDto {
	private Long id;
	private String email;
	private String name;
	private UserRole userRole;
}
