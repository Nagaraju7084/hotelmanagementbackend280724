package com.hotel.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.backend.entity.User;
import com.hotel.backend.role.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findFirstByEmail(String email);
	Optional<User> findByUserRole(UserRole userRole);
}
