package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.model.Guardian;
import com.masters.backend.model.User;
import com.masters.backend.repository.UserRepository;

@RestController
public class UserController {
@Autowired UserRepository iUserRepository;
	
	@GetMapping("/users")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<User> getAllGuardians(){
		return iUserRepository.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/users")
	public User newGuardian(@RequestBody User user) {
		user.setSuspended(0);
		return iUserRepository.save(user);
	}
	
	@GetMapping("/user/find")
	@CrossOrigin(origins = "http://localhost:8081")
	public User getUserByEmail(@RequestParam(name = "email", required=true) String email) {
		return iUserRepository.findByUsernameAndSuspended(email, 0);
	}
}
