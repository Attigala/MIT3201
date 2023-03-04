package com.masters.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.RequestedClassDto;
import com.masters.backend.dto.StudentClassStatusDto;
import com.masters.backend.dto.TeacherHomeScheduleDto;
import com.masters.backend.dto.TeacherOverviewDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.Hall;
import com.masters.backend.model.NotifyClassSubscriber;
import com.masters.backend.model.OnSiteRegular;
import com.masters.backend.model.OnlineRegular;
import com.masters.backend.model.RequestedClasses;
import com.masters.backend.model.Student;
import com.masters.backend.model.Teacher;
import com.masters.backend.model.UnenrolledStudent;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.HallRepository;
import com.masters.backend.repository.NotifyClassSubscriberRepository;
import com.masters.backend.repository.OnSiteRegularRepository;
import com.masters.backend.repository.OnlineRegularRepository;
import com.masters.backend.repository.RequestedClassRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.TeacherRepository;
import com.masters.backend.repository.UnenrolledStudentRepository;

@Service
public class ClassContractService {

	@Autowired
	ClassContractRepository iClassContractRepository;
	@Autowired
	RequestedClassRepository iRequestedClassesRepository;
	@Autowired
	OnlineRegularRepository iOnlineRegularRepository;
	@Autowired
	OnSiteRegularRepository iOnsiteOnSiteRegularRepository;
	@Autowired
	TeacherRepository iTeacherRepository;
	@Autowired HallRepository iHallRepository;
	@Autowired StudentRepository iStudentRepository;
	@Autowired StudentClassRepository iStudentClassRepository;
	@Autowired NotifyClassSubscriberRepository iNotifyClassSubscriberRepository;
	@Autowired UnenrolledStudentRepository iUnenrolledStudentRepository;
	public static final String CLASS_SUBSCRIBER_NOTIFICATION_STATUS_NEW = "NEW";
	
	public ClassContract create(ClassContract aClassContract) {
		return iClassContractRepository.save(aClassContract);
	}

	public List<ClassContract> findAll() {
		return iClassContractRepository.findAll();
	}

	public List<ClassContract> findByTeacher(Teacher teacher) {
		return iClassContractRepository.findByTeacher(teacher);
	}

	public List<ClassContract> findByParms(Map<String, Object> aParams) {
		String medium = (String) aParams.get(ClassParameter.MEDIUM.name());
		String subject = (String) aParams.get(ClassParameter.SUBJECT.name());
		Integer grade = (Integer) aParams.get(ClassParameter.GRADE.name());

		if (medium != null) {
		}

		List<ClassContract> classContracts = iClassContractRepository.findAll(new Specification<ClassContract>() {

			@Override
			public Predicate toPredicate(Root<ClassContract> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (medium != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medium"), medium)));
				}
				if (subject != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("subject"), subject)));
				}
				if (grade != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("grade"), grade)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		});
		System.out.println(medium);
		System.out.println(subject);
		System.out.println(grade);
		if (classContracts.isEmpty()) {
			RequestedClasses requestedClasses = new RequestedClasses();
			requestedClasses.setGrade(grade);
			requestedClasses.setMedium(medium.toUpperCase());
			requestedClasses.setSubject(subject.toUpperCase());
			requestedClasses.setYear(LocalDate.now().getYear());
			RequestedClasses available = iRequestedClassesRepository.findOne(Example.of(requestedClasses)).orElse(null);
			requestedClasses.setFrequency(available == null ? 0 : available.getFrequency() + 1);
			if (available != null) {
				requestedClasses.setId(available.getId());
			}
			iRequestedClassesRepository.save(requestedClasses);
		}
		return classContracts;
	}

	public List<RequestedClasses> getRequestedClasses() {
		RequestedClasses requestedClasses = new RequestedClasses();
		requestedClasses.setYear(LocalDate.now().getYear());
		return iRequestedClassesRepository.findAll(Example.of(requestedClasses));
	}

	public List<TeacherOverviewDto> getTeacherOverview(String teacherEmail) {
		List<TeacherOverviewDto> teacheroverviews = new ArrayList<TeacherOverviewDto>();
		Teacher teacher = iTeacherRepository.findFirstByEmail(teacherEmail);
		if (teacher != null) {
			List<ClassContract> teacherClasses = iClassContractRepository.findByTeacher(teacher);
			for (ClassContract contract : teacherClasses) {
				OnSiteRegular onsite = iOnsiteOnSiteRegularRepository.findFirstByClassContract(contract.getId());
				OnlineRegular online = iOnlineRegularRepository.findFirstByClassContract(contract.getId());
				TeacherOverviewDto teacherOverviewDto = new TeacherOverviewDto();
				teacherOverviewDto.setClassId(contract.getId());
				teacherOverviewDto.setClassName(contract.getSubject() + " " + contract.getMedium() + "-" + contract.getGrade());
				TeacherHomeScheduleDto scheduleDto = null;
				if (onsite != null) {
					scheduleDto = new TeacherHomeScheduleDto();
					scheduleDto.setDay(onsite.getDay());
					scheduleDto.setTime(onsite.getTimeSlot());
					Hall hall = iHallRepository.findById(onsite.getHallId()).orElse(null);
					scheduleDto.setHall(hall != null? hall.getHallName() : "");					
				} else if (online != null) {
					scheduleDto = new TeacherHomeScheduleDto();
					scheduleDto.setDay(online.getDay());
					scheduleDto.setTime(online.getTimeSlot());
				}
				teacherOverviewDto.setSchedule(scheduleDto);
				teacheroverviews.add(teacherOverviewDto);
			}
		}

		return teacheroverviews;
	}
	
	public List<TeacherOverviewDto> getStudentOverview(String studentEmail) {
		List<TeacherOverviewDto> overviews = new ArrayList<>();
		Student student = iStudentRepository.findFirstByEmail(studentEmail);
		if (student != null) {
			List<Long> studentClassIds = iStudentClassRepository.findByStudentId(student.getId()).stream().map(classes -> {
				return classes.getClassContractId();
			}).collect(Collectors.toList());
			if(studentClassIds != null && !studentClassIds.isEmpty()) {
				List<ClassContract> classContracts = iClassContractRepository.findByIdIn(studentClassIds);
				for (ClassContract classContract: classContracts) {
					OnSiteRegular onsite = iOnsiteOnSiteRegularRepository.findFirstByClassContract(classContract.getId());
					OnlineRegular online = iOnlineRegularRepository.findFirstByClassContract(classContract.getId());
					TeacherOverviewDto teacherOverviewDto = new TeacherOverviewDto();
					teacherOverviewDto.setClassId(classContract.getId());
					teacherOverviewDto.setClassName(classContract.getSubject() + " " + classContract.getMedium() + "-" + classContract.getGrade());
					TeacherHomeScheduleDto scheduleDto = null;
					if (onsite != null) {
						scheduleDto = new TeacherHomeScheduleDto();
						scheduleDto.setDay(onsite.getDay());
						scheduleDto.setTime(onsite.getTimeSlot());
						Hall hall = iHallRepository.findById(onsite.getHallId()).orElse(null);
						scheduleDto.setHall(hall != null? hall.getHallName() : "");					
					} else if (online != null) {
						scheduleDto = new TeacherHomeScheduleDto();
						scheduleDto.setDay(online.getDay());
						scheduleDto.setTime(online.getTimeSlot());
					}
					teacherOverviewDto.setSchedule(scheduleDto);
					overviews.add(teacherOverviewDto);
				}
			}
		}
		return overviews;
	}
	
	public List<RequestedClasses> getRequestedClassForYear() {
		return iRequestedClassesRepository.findByYear(LocalDate.now().getYear());
	}
	
	public NotifyClassSubscriber saveNotification(RequestedClassDto request) {
		NotifyClassSubscriber notifyClassSubscriber = new NotifyClassSubscriber();
		notifyClassSubscriber.setGrade(request.getGrade());
		notifyClassSubscriber.setMedium(request.getMedium());
		notifyClassSubscriber.setSubject(request.getSubject());
		notifyClassSubscriber.setRequestedClassId(request.getId());
		notifyClassSubscriber.setMessage(request.getMessage());
		notifyClassSubscriber.setStatus(CLASS_SUBSCRIBER_NOTIFICATION_STATUS_NEW);
		
		iNotifyClassSubscriberRepository.save(notifyClassSubscriber);
		return notifyClassSubscriber;
	}
	
	public List<StudentClassStatusDto> getStudentStatus(Long classId){
		List<StudentClassStatusDto> studentClassDtos = new ArrayList<>();
		
		ClassContract classContract = iClassContractRepository.findById(classId).orElse(null);
		if (classContract != null) {
			List<Long> studentIds = iStudentClassRepository.findByClassContractId(classContract.getId()).stream().map(studentClass -> studentClass.getStudentId()).collect(Collectors.toList());
			
			List<Student> enrolledStudents = iStudentRepository.findAllById(studentIds);
			
			for (Student student : enrolledStudents) {
				StudentClassStatusDto studentClassStatusDto = new StudentClassStatusDto();
				studentClassStatusDto.setClassContractId(classId);
				studentClassStatusDto.setContactNumber(student.getGuardian().getContactNumber());
				studentClassStatusDto.setStatus("Enrolled");
				studentClassStatusDto.setStudentId(student.getId());
				studentClassStatusDto.setStudentName(student.getFirstName() + " " + student.getLastName());
				studentClassDtos.add(studentClassStatusDto);
			}
			
			List<UnenrolledStudent> unenrolledStudents = iUnenrolledStudentRepository.findByClassContractId(classId);
			for (UnenrolledStudent student : unenrolledStudents) {
				StudentClassStatusDto studentClassStatusDto = new StudentClassStatusDto();
				studentClassStatusDto.setClassContractId(classId);
				studentClassStatusDto.setContactNumber(student.getContactNumber());
				studentClassStatusDto.setStatus("Un Enrolled");
				studentClassStatusDto.setStudentId(student.getId());
				studentClassStatusDto.setStudentName(student.getStudentFirstName() + " " + student.getStudentLastName());
				studentClassDtos.add(studentClassStatusDto);
			}
		}
		
		return studentClassDtos;
	}
}
