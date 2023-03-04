package com.masters.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameAndSuspended(String username, Integer suspended);

}
