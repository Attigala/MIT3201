package com.masters.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.ClassSuggestions;
import com.masters.backend.dto.ManualOnsiteRequest;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ClassRequest;
import com.masters.backend.model.ExtraClassNotification;
import com.masters.backend.model.Hall;
import com.masters.backend.model.OnSiteRegular;
import com.masters.backend.model.OnlineExtra;
import com.masters.backend.model.OnlineRegular;
import com.masters.backend.model.OnsiteExtra;
import com.masters.backend.model.StudentClass;
import com.masters.backend.model.Teacher;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.ClassRequestRepository;
import com.masters.backend.repository.ExtraClassNotificationRepository;
import com.masters.backend.repository.HallRepository;
import com.masters.backend.repository.OnSiteRegularRepository;
import com.masters.backend.repository.OnlineExtraRepository;
import com.masters.backend.repository.OnlineRegularRepository;
import com.masters.backend.repository.OnsiteExtraRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.TeacherRepository;

@Service
public class ClassRequestService {
	
	private static final String OK_TO_BOOK = "Ok to Book";
	private static final String WARNING_SAME_GRADE_BOOKED = "Class in Same Grade Booked";
	private static final String STATUS_NEW = "NEW";
	private static final String STATUS_PROCESSED = "PROCESSED";
	@Autowired ClassRequestRepository iClassRequestRepository;
	@Autowired TeacherRepository iTeacherRepository;
	@Autowired ClassContractRepository iClassContractRepository;
	@Autowired OnlineRegularRepository iOnlineRegularRepository;
	@Autowired HallRepository iHallRepository;
	@Autowired StudentClassRepository iStudentClassRepository;
	@Autowired OnSiteRegularRepository iOnsiteRegularRepository;
	@Autowired OnsiteExtraRepository iOnsiteExtraRepository;
	@Autowired OnlineExtraRepository iOnlineExtraRepository;
	@Autowired ExtraClassNotificationRepository iExtraClassNotificationRepository;
	
	public ClassRequest create(ClassRequest request) {
		
		request.setStatus(STATUS_NEW);
		iClassRequestRepository.save(request);
		return request;
	}
	
	public List<ClassRequest> getForClass(Long classId) {
		return iClassRequestRepository.findByClassId(classId);
	}
	
	public List<ClassRequest> getForTeacher(String email) {
		Teacher teacher = iTeacherRepository.findFirstByEmail(email);
		if(teacher != null) {
			List<ClassContract> classes = iClassContractRepository.findByTeacher(teacher);
			if(classes != null && !classes.isEmpty()) {
				List<Long> classIds = classes.stream().map(classContract -> {
					return classContract.getId();
				}).collect(Collectors.toList());
				List<ClassRequest> classRequests = iClassRequestRepository.findByClassIdIn(classIds);
				for(ClassRequest classRequest : classRequests) {
					ClassContract classContract = getContract(classRequest.getClassId(), classes);
					if(classContract != null) {
						classRequest.setClassName(classContract.getSubject() + " " +classContract.getMedium() + "-" + classContract.getGrade());
					}
					
				}
				return classRequests;
			}
		}
		return null;
	}
	
	public List<ClassRequest> getAllUnscheduledRequests() {
		
		List<ClassRequest> unscheduledClasses = iClassRequestRepository.findByStatus(STATUS_NEW);
		for(ClassRequest request: unscheduledClasses) {
			ClassContract classContract = iClassContractRepository.findById(request.getClassId()).orElse(null);
			if (classContract != null) {
				request.setClassName(classContract.getSubject() + " " +classContract.getMedium() + "-" + classContract.getGrade());
			}
		}
		return unscheduledClasses;
		
	}
	
	public List<ClassRequest> getAllUnscheduledOnsiteRequests() {
		List<ClassRequest> unScheduledRequestsOnsite = iClassRequestRepository.findByStatusAndMode(STATUS_NEW, "Onsite");
		for (ClassRequest request: unScheduledRequestsOnsite) {
			ClassContract classContract = iClassContractRepository.findById(request.getClassId()).orElse(null);
			if (classContract != null) {
				request.setClassName(classContract.getSubject() + " " +classContract.getMedium() + "-" + classContract.getGrade());
			}
		}
		return unScheduledRequestsOnsite;
	}
	
	public List<ClassSuggestions> getSuggestion(Long requestId) {
		ClassRequest request = iClassRequestRepository.findById(requestId).orElse(null);
		List<ClassSuggestions> suggestions = new ArrayList<ClassSuggestions>();
		if (request != null) {
			ClassContract requestedClass = iClassContractRepository.findById(request.getClassId()).orElse(null);
			String day = request.getDate().toLocalDate().getDayOfWeek().name();
			String timeSlot = request.getTime();
			if (request.getMode().equalsIgnoreCase("Online")) {
				OnlineRegular alreadyScheduledRegular = iOnlineRegularRepository.findFirstByDayAndTimeSlot(day, timeSlot);
				if (alreadyScheduledRegular != null) {
					ClassContract alreadyScheduledRegClass = iClassContractRepository.findById(alreadyScheduledRegular.getClassContract()).orElse(null);
					if (alreadyScheduledRegClass != null) {
						if (alreadyScheduledRegClass.getGrade() == requestedClass.getGrade()) {
							ClassSuggestions classSuggestion = new ClassSuggestions();
							classSuggestion.setClassId(request.getClassId());
							classSuggestion.setHallName("ONLINE");
							classSuggestion.setDate(request.getDate());
							classSuggestion.setMode("Online");
							classSuggestion.setRequestId(requestId);
							classSuggestion.setTimeslot(request.getTime());
							classSuggestion.setStatus(WARNING_SAME_GRADE_BOOKED);
							suggestions.add(classSuggestion);
							return suggestions;
						} else {
							ClassSuggestions classSuggestion = new ClassSuggestions();
							classSuggestion.setClassId(request.getClassId());
							classSuggestion.setHallName("ONLINE");
							classSuggestion.setDate(request.getDate());
							classSuggestion.setMode("Online");
							classSuggestion.setRequestId(requestId);
							classSuggestion.setTimeslot(request.getTime());
							classSuggestion.setStatus(OK_TO_BOOK);
							suggestions.add(classSuggestion);
							return suggestions;
						}
					}
				} else {
					ClassSuggestions classSuggestion = new ClassSuggestions();
					classSuggestion.setClassId(request.getClassId());
					classSuggestion.setHallName("ONLINE");
					classSuggestion.setDate(request.getDate());
					classSuggestion.setMode("Online");
					classSuggestion.setRequestId(requestId);
					classSuggestion.setTimeslot(request.getTime());
					classSuggestion.setStatus(OK_TO_BOOK);
					suggestions.add(classSuggestion);
					return suggestions;
				}
			} else {
				List<Hall> usableHalls = getHalls(studentCount(request.getClassId()));
				ClassSuggestions classSuggestion = null;
				for(Hall hall : usableHalls) {
					classSuggestion = findOnsiteSuggestion(timeSlot, day, hall, request);
					if (classSuggestion != null) {
						break;
					}
				}
				if (classSuggestion == null) {
					String endTime = timeSlot.split("-")[1].split(":")[0];
					if (endTime != null) {
						Integer startTime = Integer.valueOf(endTime);
						Integer endTimeInt = startTime + 2;
						for(Hall hall : usableHalls) {
							classSuggestion = classSuggestion = findOnsiteSuggestion(startTime +":"+ endTimeInt, day, hall, request);
							if (classSuggestion != null) {
								break;
							}
						}
						
					}
				}
				suggestions.add(classSuggestion);
				return suggestions;
			}
		}
		return null;
	}
	
	public OnsiteExtra scheduleOnsiteExtra(ManualOnsiteRequest classRequest) {
		ClassContract classContract = iClassContractRepository.findById(classRequest.getClassContract()).orElse(null);
		ClassRequest request = iClassRequestRepository.findById(classRequest.getRequestId()).orElse(null);
		System.out.println(classRequest.getClassContract());
		System.out.println("classContract"+ classContract==null);
		if (classContract != null && classRequest != null) {
			OnsiteExtra onsiteExtra = new OnsiteExtra();
			onsiteExtra.setClassContract(classContract.getId());
			onsiteExtra.setTeacherId(classContract.getTeacher().getId());
			onsiteExtra.setDate(classRequest.getDate());
			onsiteExtra.setTimeSlot(classRequest.getTimeSlot());
			onsiteExtra.setRequestId(request.getId());
			onsiteExtra.setHallId(classRequest.getHallId());
			iOnsiteExtraRepository.save(onsiteExtra);
			
			request.setStatus(STATUS_PROCESSED);
			iClassRequestRepository.save(request);
			
			ExtraClassNotification extraClassNotification = new ExtraClassNotification();
			extraClassNotification.setClassId(classContract.getId());
			extraClassNotification.setMode("ONSITE");
			extraClassNotification.setStatus("NEW");
			extraClassNotification.setExtraClassId(onsiteExtra.getId());
			extraClassNotification.setDate(classRequest.getDate());
			extraClassNotification.setTime(classRequest.getTimeSlot());
			extraClassNotification.setClassName(classContract.getSubject() + " " + classContract.getMedium() + " " + classContract.getGrade());
			iExtraClassNotificationRepository.save(extraClassNotification);
			return onsiteExtra;
		}
		
		return null;
	}
	
	public OnlineExtra scheduleOnlineExtra(ManualOnsiteRequest classRequest) {
		ClassContract classContract = iClassContractRepository.findById(classRequest.getClassContract()).orElse(null);
		ClassRequest request = iClassRequestRepository.findById(classRequest.getRequestId()).orElse(null);
		if (classContract != null && classRequest != null) {
			OnlineExtra onlineExtra = new OnlineExtra();
			onlineExtra.setClassContract(classContract.getId());
			onlineExtra.setDate(classRequest.getDate());
			onlineExtra.setRequestId(classRequest.getRequestId());
			onlineExtra.setTeacherId(classContract.getTeacher().getId());
			onlineExtra.setTimeSlot(classRequest.getTimeSlot());
			iOnlineExtraRepository.save(onlineExtra);
			
			request.setStatus(STATUS_PROCESSED);
			iClassRequestRepository.save(request);
			
			ExtraClassNotification extraClassNotification = new ExtraClassNotification();
			extraClassNotification.setClassId(classContract.getId());
			extraClassNotification.setMode("ONLINE");
			extraClassNotification.setStatus("NEW");
			extraClassNotification.setExtraClassId(onlineExtra.getId());
			extraClassNotification.setDate(classRequest.getDate());
			extraClassNotification.setTime(classRequest.getTimeSlot());
			extraClassNotification.setClassName(classContract.getSubject() + " " + classContract.getMedium() + " " + classContract.getGrade());
			iExtraClassNotificationRepository.save(extraClassNotification);
			return onlineExtra;
		}
		return null;
	}
	
	private ClassContract getContract(Long classId, List<ClassContract> classContracts) {
		for( ClassContract contract : classContracts) {
			if(contract.getId().equals(classId)) {
				return contract;
			}
		}
		return null;
	}
	
	private List<Hall> getHalls(long numOfStudents) {
		List<Hall> halls = iHallRepository.findByCapacityGreaterThan((int)numOfStudents);
		return halls;
	}
	
	private long studentCount(Long classContractId) {
		
		StudentClass studentClass = new StudentClass();
		studentClass.setClassContractId(classContractId);
		long count = iStudentClassRepository.count(Example.of(studentClass));
		
		return count;
		
	}
	
	private ClassSuggestions findOnsiteSuggestion(String timeSlot, String day, Hall hall, ClassRequest request) {
		ClassSuggestions classSuggestion = null;
		OnSiteRegular onsiteRegular = new OnSiteRegular();
		onsiteRegular.setDay(day);
		onsiteRegular.setTimeSlot(timeSlot);
		onsiteRegular.setHallId(hall.getId());
		List<OnSiteRegular> regularClass = iOnsiteRegularRepository.findAll(Example.of(onsiteRegular));
		if (regularClass.isEmpty()) {
			OnsiteExtra onsiteExtra = new OnsiteExtra();
			onsiteExtra.setHallId(hall.getId());
			onsiteExtra.setDate(request.getDate());
			onsiteExtra.setTimeSlot(timeSlot);
			List<OnsiteExtra> onsiteExtras = iOnsiteExtraRepository.findAll(Example.of(onsiteExtra));
			if (onsiteExtras.isEmpty()) {
				classSuggestion = new ClassSuggestions();
				classSuggestion.setClassId(request.getClassId());
				classSuggestion.setHallName(hall.getHallName());
				classSuggestion.setHallNumber(hall.getId());
				classSuggestion.setDate(request.getDate());
				classSuggestion.setMode("Onsite");
				classSuggestion.setRequestId(request.getClassId());
				classSuggestion.setTimeslot(request.getTime());
				classSuggestion.setStatus(OK_TO_BOOK);
			}
		}
		
		return classSuggestion;
	}
	
}
