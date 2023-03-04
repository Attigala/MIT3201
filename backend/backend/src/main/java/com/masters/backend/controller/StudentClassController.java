package com.masters.backend.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.dto.ClassStatisticsDto;
import com.masters.backend.dto.PendingAssignmentDto;
import com.masters.backend.dto.StudentClassDto;
import com.masters.backend.dto.StudentClassSuspensionDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.Guardian;
import com.masters.backend.model.StudentClass;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.service.StudentClassService;

@RestController
public class StudentClassController {
	
	@Autowired StudentClassRepository iStudentClassRepository;
	@Autowired StudentClassService iStudentClassService;
	@Autowired GuardianRepository iGuardianRepository;
	@Autowired StudentRepository iStudentRepository;
	
	@GetMapping("/studentClass")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<StudentClass> getAllStudents(){
		return iStudentClassRepository.findAll();
	}
	
	
	@PostMapping("/studentClass")
	@CrossOrigin(origins = "http://localhost:8081")
	public StudentClass newStudent(@RequestBody StudentClass student) {
		System.out.println(student);
		student.setMonth(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
		student.setSuspended(0);
		return iStudentClassRepository.save(student);
	}
	
	@GetMapping("/studentClass/yearlyStatistics")
	@CrossOrigin(origins="http://localhost:8081")
	public List<ClassStatisticsDto> getYearlyStatistics() {
		System.out.println("Statistics endpoint");
		return iStudentClassService.getMonthlyStatistics();
	}
	
	@GetMapping("/studentClass/student")
	@CrossOrigin(origins="http://localhost:8081")
	public List<ClassContract> getStudentClasses(@RequestParam(name = "studentId", required=true) Long id){
		return iStudentClassService.findByStudentId(id);
	}
	
	@GetMapping("/studentClass/guardian")
	@CrossOrigin(origins="http://localhost:8081")
	public List<StudentClassDto> getStudentClassesByGuardian(@RequestParam(name = "guardianId", required=true) Long id){
		return iStudentClassService.findByGuardianId(id);
	}
	
	@GetMapping("/studentClass/guardian/email")
	@CrossOrigin(origins="http://localhost:8081")
	public List<StudentClassDto> getStudentClassesByGuardianEmail(@RequestParam(name = "email", required=true) String email){
		
		return iStudentClassService.findByGuardian(iGuardianRepository.findFirstByEmail(email));
	}

	
	@PutMapping("/studentClass/delete")
	@CrossOrigin(origins="http://localhost:8081")
	public ResponseEntity deleteStudentFromClass(@RequestBody StudentClassDto student) {
		try {
			iStudentClassService.unEnrollStudentFromClass(student);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/guardian/suspend/all")
	@CrossOrigin(origins="http://localhost:8081")
	public Guardian suspendGuardianAndStudent(@RequestParam(name="id", required = true) Long id) {
		return iStudentClassService.suspendGuardian(id);
	}
	
	@PutMapping("/guardian/suspend/studentClass")
	@CrossOrigin(origins="http://localhost:8081")
	public StudentClass suspendStudentFromClass(@RequestParam(name="studentId", required = true) Long studentId, @RequestParam(name="classContractId", required = true) Long classContractId) {
		return iStudentClassService.suspendStudentClass(studentId, classContractId);
	}
	
	@GetMapping("/guardian/suspended")
	@CrossOrigin(origins="http://localhost:8081")
	public List<Guardian> getSuspendedGuardians() {
		return iStudentClassService.getSuspendedGuardian();
	}
	
	@GetMapping("/studentClass/suspended")
	@CrossOrigin(origins="http://localhost:8081")
	public List<StudentClassSuspensionDto> getSuspendedStudentClasses() {
		return iStudentClassService.getSuspendedStudentClasses();
	}
	
	@GetMapping("/studentClass/classContract")
	@CrossOrigin(origins="http://localhost:8081")
	public List<StudentClassDto> getStudentsInClass(@RequestParam (name="classId", required = true) Long classId) {
		return iStudentClassService.getStudentsInClass(classId);
	}
	
	@GetMapping("/studentClass/assignment/pending")
	@CrossOrigin(origins="http://localhost:8081")
	public List<PendingAssignmentDto> getPendingAssignmentForChildren (@RequestParam(name="email", required = true) String email) {
		Guardian guardian = iGuardianRepository.findFirstByEmail(email);
		return iStudentClassService.getPendingAssignmentsForStudent(iStudentRepository.findByGuardian(guardian));
	}
}
