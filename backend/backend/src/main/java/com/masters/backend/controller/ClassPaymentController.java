package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.dto.UnpaidDto;
import com.masters.backend.model.ClassPayment;
import com.masters.backend.model.Student;
import com.masters.backend.repository.ClassPaymentRepository;
import com.masters.backend.service.ClassPaymentService;

@RestController
public class ClassPaymentController {
	
	@Autowired ClassPaymentRepository iClassPaymentRepository;
	@Autowired ClassPaymentService iClassPaymentService;
	
	@GetMapping("/classPayments")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassPayment> getAllClassPayments() {
		return iClassPaymentRepository.findAll();
	}
	
	@GetMapping("/classPayments/filter")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassPayment> getClassPaymentsForYearAndMonth(@RequestParam String month, @RequestParam Integer year) {
		return iClassPaymentService.findByMonthAndYear(month, year);
	}
	
	@PostMapping("/classPayments")
	@CrossOrigin(origins = "http://localhost:8081")
	public ClassPayment save(@RequestBody ClassPayment classPayment) {
		return iClassPaymentService.createPayment(classPayment);
	}
	
	@GetMapping("/classPayments/unpaid")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<UnpaidDto> getUnpaidStudent() {
		return iClassPaymentService.getUnpaidStudentList();
	}
	
	@GetMapping("/classPayments/unpaid/guardian")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<UnpaidDto> getUnpaidForGuardian(@RequestParam (name="email", required=true) String email) {
		return iClassPaymentService.unpaidForGuardian(email);
	}
	
	@GetMapping("/classPayments/unpaid/currentMonth")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<UnpaidDto> getUnpaidForCurrentMonth() {
		return iClassPaymentService.unpaidThisMonth();
	}

}
