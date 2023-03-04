package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.model.ClassPayment;
import com.masters.backend.model.ExemptFromPayment;
import com.masters.backend.repository.ClassPaymentRepository;
import com.masters.backend.repository.ExemptFromPaymentRepository;
import com.masters.backend.service.ClassPaymentService;

@RestController
public class ExemptFromPaymentController {
	
	@Autowired ExemptFromPaymentRepository iExempClassPaymentRepository;
	
	@GetMapping("/exempt")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ExemptFromPayment> getAllClassPayments() {
		return iExempClassPaymentRepository.findAll();
	}
	
	@PostMapping("/exempt")
	@CrossOrigin(origins = "http://localhost:8081")
	public ExemptFromPayment save(@RequestBody ExemptFromPayment classPayment) {
		return iExempClassPaymentRepository.save(classPayment);
	}

}