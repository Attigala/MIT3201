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
import com.masters.backend.model.Student;
import com.masters.backend.model.User;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.UserRepository;

@RestController
public class StudentController {
	@Autowired StudentRepository iStudentRepository;
	@Autowired GuardianRepository iGuardianRepositry;
	@Autowired UserRepository iUserRepository;
	
	@GetMapping("/students")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<Student> getAllStudents(){
		return iStudentRepository.findAll();
	}
	
	@GetMapping("/student/guardian")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<Student> getAllStudentsForGuardian(@RequestParam (name="email", required=true) String email){
		Guardian guardian = iGuardianRepositry.findFirstByEmail(email);
		if(guardian == null) {
			return null;
		}
		return iStudentRepository.findByGuardian(guardian);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/students")
	public Student newStudent(@RequestBody Student student) {
		User user = new User();
		user.setUsername(student.getEmail());
		user.setPassword(student.getContactNumber());
		user.setRole("student");
		user.setSuspended(0);
		student.setSuspended(0);
		iUserRepository.save(user);
		
		Student student1 =  iStudentRepository.save(student);
		System.out.println("suspended: " + student1.getSuspended());
		return student1;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/guardian/students")
	public Student newStudentForGuardian(@RequestBody Student student, @RequestParam (name = "email", required = false) String aEmail) {
		Guardian guardian = iGuardianRepositry.findFirstByEmail(aEmail);
		student.setGuardian(guardian);
		User user = new User();
		user.setUsername(student.getEmail());
		user.setPassword(student.getContactNumber());
		user.setRole("student");
		user.setSuspended(0);
		student.setSuspended(0);
		iUserRepository.save(user);
		System.out.println(student.toString());
		return iStudentRepository.save(student);
	}
	
	@GetMapping("/students/student")
	@CrossOrigin(origins = "http://localhost:8081")
	public Student getStudentByEmail(@RequestParam(name="email", required=true) String email){
		return iStudentRepository.findFirstByEmail(email);
	}
}
