package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.model.Teacher;
import com.masters.backend.model.User;
import com.masters.backend.repository.TeacherRepository;
import com.masters.backend.repository.UserRepository;

@RestController
public class TeacherController {
	
	@Autowired TeacherRepository iTeacherRepository;
	@Autowired UserRepository iUserRepository;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/teachers")
	List<Teacher> getAll(){
		return iTeacherRepository.findAll();
	}
	
	@PostMapping("/teachers")
	@CrossOrigin(origins = "http://localhost:8081")
	Teacher newTeacher(@RequestBody Teacher aTeacher) {
		User user = new User();
		user.setUsername(aTeacher.getEmail());
		user.setPassword(aTeacher.getNic());
		user.setRole("teacher");
		user.setSuspended(0);
		iUserRepository.save(user);
		return iTeacherRepository.save(aTeacher);
	}

}
