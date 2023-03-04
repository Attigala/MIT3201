package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.model.Hall;
import com.masters.backend.repository.HallRepository;
import com.masters.backend.service.EmailService;

@RestController
public class HallController {
	
	@Autowired private HallRepository iHallRepository;
	@Autowired private EmailService iEmailService;
	
	@GetMapping("/halls")
	@CrossOrigin(origins = "http://localhost:8081")
	private List<Hall> getAllHalls(){
		return iHallRepository.findAll();
	}
	
	@PostMapping("/halls")
	@CrossOrigin(origins = "http://localhost:8081")
	public Hall saveHall(@RequestBody Hall hall) {
		return iHallRepository.save(hall);
	}

}
