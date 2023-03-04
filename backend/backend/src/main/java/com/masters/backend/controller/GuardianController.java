package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.model.Guardian;
import com.masters.backend.model.User;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.UserRepository;
import com.masters.backend.service.StudentClassService;

@RestController
public class GuardianController {
	@Autowired GuardianRepository iGuardianRepository;
	@Autowired UserRepository iUserRepository;
	@Autowired StudentClassService iStudentClassService;
	
	@GetMapping("/guardians")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<Guardian> getAllGuardians(){
		return iGuardianRepository.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/guardians")
	public Guardian newGuardian(@RequestBody Guardian guardian) {
		User user = new User();
		user.setUsername(guardian.getEmail());
		user.setPassword(guardian.getNic());
		user.setRole("guardian");
		user.setSuspended(0);
		iUserRepository.save(user);
		guardian.setSuspended(0);
		return iGuardianRepository.save(guardian);
	}
}
