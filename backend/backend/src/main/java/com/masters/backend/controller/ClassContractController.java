package com.masters.backend.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;
import com.masters.backend.dto.ClassSuggestions;
import com.masters.backend.dto.ManualOnsiteRequest;
import com.masters.backend.dto.OnlineMeetingDto;
import com.masters.backend.dto.RequestedClassDto;
import com.masters.backend.dto.StudentClassStatusDto;
import com.masters.backend.dto.TeacherOverviewDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ClassRequest;
import com.masters.backend.model.Meeting;
import com.masters.backend.model.NotifyClassSubscriber;
import com.masters.backend.model.OnlineExtra;
import com.masters.backend.model.OnsiteExtra;
import com.masters.backend.model.RequestSubscription;
import com.masters.backend.model.RequestedClasses;
import com.masters.backend.model.Teacher;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.MeetingRepository;
import com.masters.backend.repository.RequestSubscriptionRepository;
import com.masters.backend.service.ClassContractService;
import com.masters.backend.service.ClassParameter;
import com.masters.backend.service.ClassRequestService;
import com.masters.backend.service.GoogleCalendarService;

@RestController
public class ClassContractController {
	@Autowired ClassContractService iClassContractService;
	@Autowired GoogleCalendarService iGoogleCalendarSerivce;
	@Autowired MeetingRepository iMeeetingRepository;
	@Autowired ClassRequestService iClassRequestService;
	@Autowired RequestSubscriptionRepository iRequestSubscriptionRepository;
	
	@GetMapping("/classContracts")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassContract> getAllStudents(){
		return iClassContractService.findAll();
	}
	
	@GetMapping("/classContracts/filter")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassContract> getAllStudentsBy(@RequestParam (name = "subject", required = false) String aSubject, 
			@RequestParam (name = "grade", required = false) Integer aGrade, 
			@RequestParam (name = "medium", required = false) String aMedium){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ClassParameter.MEDIUM.name(), aMedium);
		params.put(ClassParameter.SUBJECT.name(), aSubject);
		params.put(ClassParameter.GRADE.name(), aGrade);
		return iClassContractService.findByParms(params);
	}	
	
	@GetMapping("/classContracts/requested")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<RequestedClasses> getRequested(){
		return iClassContractService.getRequestedClasses();
	}	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/classContracts")
	public ClassContract newStudent(@RequestBody ClassContract classContract) {
		return iClassContractService.create(classContract);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/classByTeacher")
	public List<ClassContract> findByTeacher(@RequestBody Teacher teacher) {
		return iClassContractService.findByTeacher(teacher);
	}
	
	@PostMapping("/classContracts/test")
	@CrossOrigin(origins = "http://localhost:8081")
	public HttpStatus test(@RequestBody OnlineMeetingDto onlineMeetingDto) {
		try {
			Event event = iGoogleCalendarSerivce.createEvent(onlineMeetingDto);
			Meeting meeting = new Meeting();
			meeting.setClassId(onlineMeetingDto.getClassId());
			meeting.setMeetingUrl(event.getHtmlLink());
			meeting.setClassDate(Date.valueOf(onlineMeetingDto.getMeetingDate()));
			meeting.setClassTime(Time.valueOf(onlineMeetingDto.getMeetingEndTime()));
			iMeeetingRepository.save(meeting);
			return HttpStatus.OK;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
	}	

	@GetMapping("/classContracts/teacher")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<TeacherOverviewDto> getOverview(@RequestParam (name = "teacher", required = false) String teacherEmail) {
		return iClassContractService.getTeacherOverview(teacherEmail);
	}
	
	@PostMapping("/classContracts/request")
	@CrossOrigin(origins = "http://localhost:8081")
	public ClassRequest create(@RequestBody ClassRequest request) {
		return iClassRequestService.create(request);
	}
	
	@GetMapping("/classContracts/request/class")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassRequest> getRequestsForClass(@RequestParam (name="classId") Long classId) {
		return iClassRequestService.getForClass(classId);
	}
	
	@GetMapping("/classContracts/request/teacher")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassRequest> getRequestsForClass(@RequestParam (name="username") String username) {
		return iClassRequestService.getForTeacher(username);
	}
	
	@GetMapping("/classContracts/student")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<TeacherOverviewDto> getStudentOverview(@RequestParam (name = "student", required = false) String studentEmail) {
		return iClassContractService.getStudentOverview(studentEmail);
	}
	
	@GetMapping("/classContracts/unscheduled")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassRequest> getUnscheduledRequests () {
		return iClassRequestService.getAllUnscheduledRequests();
	}
	@GetMapping("/classContracts/unscheduled/onsite")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassRequest> getUnscheduledOnsiteRequests () {
		return iClassRequestService.getAllUnscheduledOnsiteRequests();
	}
	
	@GetMapping("/classContracts/suggestions")
	@CrossOrigin(origins = "http://localhost:8081")
	public List<ClassSuggestions> getSuggestions(@RequestParam (name = "requestId", required = false) Long requestId) {
		return iClassRequestService.getSuggestion(requestId);
	}
	
	@PostMapping("/classContracts/onsite/manual")
	@CrossOrigin(origins = "http://localhost:8081")
	public OnsiteExtra create(@RequestBody ManualOnsiteRequest request) {
		System.out.println("inside manual request create");
		return iClassRequestService.scheduleOnsiteExtra(request);
	}
	
	@PostMapping("/classContracts/online/manual")
	@CrossOrigin(origins ="http://localhost:8081")
	public OnlineExtra createManualOnline(@RequestBody ManualOnsiteRequest request) {
		return iClassRequestService.scheduleOnlineExtra(request);
	}
	
	@PostMapping("/classContracts/notify")
	@CrossOrigin(origins ="http://localhost:8081")
	public RequestSubscription createNotify(@RequestBody RequestSubscription request) {
		return iRequestSubscriptionRepository.save(request);
	}
	
	@GetMapping("/classContracts/notify")
	@CrossOrigin(origins ="http://localhost:8081")
	public List<RequestSubscription> getAllNotifications() {
		return iRequestSubscriptionRepository.findAll();
	}
	
	@GetMapping("/classContracts/notify/year")
	@CrossOrigin(origins ="http://localhost:8081")
	public List<RequestedClasses> getRequestedClassesForYear() {
		return iClassContractService.getRequestedClassForYear();
	}
	
	@PostMapping("/classContracts/notify/message")
	@CrossOrigin(origins ="http://localhost:8081")
	public NotifyClassSubscriber sendMessageToClassSubscriber(@RequestBody RequestedClassDto request) {
		return iClassContractService.saveNotification(request);
	}
	
	@GetMapping("/classContracts/students/status")
	@CrossOrigin(origins ="http://localhost:8081")
	public List<StudentClassStatusDto> getStudentStatus(@RequestParam( name= "classId", required = true) Long classId) {
		return iClassContractService.getStudentStatus(classId);
	}
}
