package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.dto.RegularSchedulingDto;
import com.masters.backend.dto.TimeTableSlot;
import com.masters.backend.model.Hall;
import com.masters.backend.model.User;
import com.masters.backend.service.RegularSchedulingService;

@RestController
public class RegularSchedulingController {
	
	@Autowired RegularSchedulingService iRegularSchedulingService;
	
	@PostMapping("/regularSchedule")
	@CrossOrigin(origins = "http://localhost:8081")
	public RegularSchedulingDto saveHall(@RequestBody RegularSchedulingDto schedule) {
		return iRegularSchedulingService.createSchedule(schedule);
	}
	
	@GetMapping("/schedule/find")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<TimeTableSlot> getScheduleBetweenDates(@RequestParam(name = "start", required = true) String start,
			@RequestParam(name = "end", required = true) String end,
			@RequestParam(name = "hallId", required = true) Long hallId) {
		return iRegularSchedulingService.getSchedules(start, end, hallId);
	}
	
	@GetMapping("/schedule/online/find")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<TimeTableSlot> getOnlineScheduleBetweenDates(@RequestParam(name = "start", required = true) String start,
			@RequestParam(name = "end", required = true) String end) {
		return iRegularSchedulingService.getOnlineSchedules(start, end);
	}
}
