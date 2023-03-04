package com.masters.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.masters.backend.dto.ClassStatisticsDto;
import com.masters.backend.dto.PendingAssignmentDto;
import com.masters.backend.dto.StudentClassDto;
import com.masters.backend.dto.StudentClassSuspensionDto;
import com.masters.backend.model.Assignment;
import com.masters.backend.model.AssignmentSubmission;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.Guardian;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudentClass;
import com.masters.backend.model.SuspensionNotification;
import com.masters.backend.model.UnenrolledStudent;
import com.masters.backend.repository.AssignmentRepository;
import com.masters.backend.repository.AssignmentSubmissionRepository;
import com.masters.backend.repository.ClassContractId;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.SuspensionNotificationRepository;
import com.masters.backend.repository.UnenrolledStudentRepository;

import java.time.LocalDate;

@Service
public class StudentClassService {
	
	@Autowired private ClassContractRepository iClassContractRepository;
	@Autowired private StudentClassRepository iStudentClassRepository;
	@Autowired private GuardianRepository iGuardianRepository;
	@Autowired private StudentRepository iStudentRepository;
	@Autowired private SuspensionNotificationRepository iSuspensionNotificationRepository;
	@Autowired private AssignmentRepository iAssignmentRepository;
	@Autowired private AssignmentSubmissionRepository iAssignmentSubmissionRepository;
	@Autowired private UnenrolledStudentRepository iUnenrolledStudentRepository;
	
	private static final String SUSPENSION_STATUS_NEW = "NEW";
	
	public enum Months {
		January,
		February,
		March,
		April,
		May,
		June,
		July,
		August,
		September,
		October,
		November,
		December
	}
	
	public List<ClassStatisticsDto> getMonthlyStatistics() {
		List<ClassContract> classIds = iClassContractRepository.findAll();
		System.out.println(classIds);
		List<ClassStatisticsDto> classStatistics = new ArrayList<ClassStatisticsDto>();
		for(ClassContract classContract: classIds) {
			ClassStatisticsDto statistic = new ClassStatisticsDto();
			statistic.setSubject(classContract.getSubject());
			statistic.setGrade(classContract.getGrade().toString());
			statistic.setMedium(classContract.getMedium());
			StudentClass example = new StudentClass();
			example.setYear(LocalDate.now().getYear());
			example.setClassContractId(classContract.getId());
			statistic.setTotalStudents(iStudentClassRepository.count(Example.of(example)));
			statistic.setTeacherName(classContract.getTeacher().getFirstName() + classContract.getTeacher().getLastName());
			statistic.setClassId(classContract.getId());
			Map<String, Long> monthlyEnrollment = new HashMap<>();
			
			for(Months month : Months.values()) {
				example = new StudentClass();
				example.setClassContractId(classContract.getId());
				example.setMonth(month.name());
				example.setYear(LocalDate.now().getYear());
				monthlyEnrollment.put(month.name(), iStudentClassRepository.count(Example.of(example)));
			}
			statistic.setTotalPerMonth(monthlyEnrollment);
			classStatistics.add(statistic);
		}
		System.out.println(classStatistics.size());
		return classStatistics;
	}

	public List<ClassContract> findByStudentId(Long id) {
		List<Long> classIds = iStudentClassRepository.findByStudentId(id).stream().map(mapper -> mapper.getClassContractId()).collect(Collectors.toList());
		
		return iClassContractRepository.findByIdIn(classIds);
	}
	
	public List<StudentClassDto>findByGuardianId(Long id){
		Guardian guardian = iGuardianRepository.findById(id).orElseGet(null);
		List<StudentClassDto> studentClassDtoList = new ArrayList<StudentClassDto>();
		if(guardian != null) {
			iStudentRepository.findByGuardian(guardian).forEach(student -> { 
				findByStudentId(student.getId()).forEach(ClassContract -> {
					StudentClassDto studentClassDto = new StudentClassDto();
					studentClassDto.setStudentId(student.getId());
					studentClassDto.setStudentName(student.getFirstName()+" "+student.getLastName());
					studentClassDto.setClassId(ClassContract.getId());
					studentClassDto.setGrade(ClassContract.getGrade().toString());
					studentClassDto.setMedium(ClassContract.getMedium());
					studentClassDto.setSubject(ClassContract.getSubject());
					studentClassDtoList.add(studentClassDto);
				});
			});
		}
		return studentClassDtoList;
	}
	
	public List<StudentClassDto>findByGuardian(Guardian guardian){
		List<StudentClassDto> studentClassDtoList = new ArrayList<StudentClassDto>();
		if(guardian != null) {
			iStudentRepository.findByGuardian(guardian).forEach(student -> { 
				findByStudentId(student.getId()).forEach(ClassContract -> {
					StudentClassDto studentClassDto = new StudentClassDto();
					studentClassDto.setStudentId(student.getId());
					studentClassDto.setStudentName(student.getFirstName()+" "+student.getLastName());
					studentClassDto.setClassId(ClassContract.getId());
					studentClassDto.setGrade(ClassContract.getGrade().toString());
					studentClassDto.setMedium(ClassContract.getMedium());
					studentClassDto.setSubject(ClassContract.getSubject());
					studentClassDtoList.add(studentClassDto);
				});
			});
		}
		return studentClassDtoList;
	}
	
	public Guardian suspendGuardian(Long id) {
		Guardian guardian = iGuardianRepository.findById(id).orElse(null);
		if (guardian != null) {
			List<Student> students = iStudentRepository.findByGuardian(guardian);
			for(Student student : students) {
				List<StudentClass> studentClasses = iStudentClassRepository.findByStudentId(student.getId());
				for(StudentClass studentClass: studentClasses) {
					studentClass.setSuspended(1);
					studentClass.setSuspendedDate(new java.sql.Date(System.currentTimeMillis()));
					iStudentClassRepository.save(studentClass);
				}
				
				student.setSuspended(1);
				student.setSuspendedDate(new java.sql.Date(System.currentTimeMillis()));
				iStudentRepository.save(student);
			}
			guardian.setSuspended(1);
			guardian.setSuspendedDate(new java.sql.Date(System.currentTimeMillis()));
			iGuardianRepository.save(guardian);
			SuspensionNotification suspensionNotification = new SuspensionNotification();
			suspensionNotification.setEmail(guardian.getEmail());
			suspensionNotification.setGuardianId(guardian.getId());
			suspensionNotification.setStatus(SUSPENSION_STATUS_NEW);
			iSuspensionNotificationRepository.save(suspensionNotification);
		}
		return guardian;
	}
	
	public StudentClass suspendStudentClass(Long studentId, Long classContractId) {
		StudentClass classContract = iStudentClassRepository.findFirstByStudentIdAndClassContractId(studentId, classContractId);
		if(classContract != null) {
			Student student = iStudentRepository.findById(studentId).orElse(null);
			if(student != null) {
				classContract.setSuspended(1);
				classContract.setSuspendedDate(new java.sql.Date(System.currentTimeMillis()));
				iStudentClassRepository.save(classContract);
				
				student.setSuspended(1);
				student.setSuspendedDate(new java.sql.Date(System.currentTimeMillis()));
				iStudentRepository.save(student);
				Guardian guardian = student.getGuardian();
				SuspensionNotification suspensionNotification = new SuspensionNotification();
				suspensionNotification.setEmail(guardian.getEmail());
				suspensionNotification.setGuardianId(guardian.getId());
				suspensionNotification.setStatus(SUSPENSION_STATUS_NEW);
				iSuspensionNotificationRepository.save(suspensionNotification);
			}
		}
		return classContract;
	}
	
	public List<Guardian> getSuspendedGuardian() {
		List<Guardian> guardians = iGuardianRepository.findBySuspended(1);
		return guardians;
	}
	
	public List<StudentClassSuspensionDto> getSuspendedStudentClasses() {
		List<StudentClass> suspensions = iStudentClassRepository.findBySuspended(1);
		List<StudentClassSuspensionDto> suspensionDtos = new ArrayList<>();
		for(StudentClass suspension : suspensions) {
			Student student = iStudentRepository.findById(suspension.getStudentId()).orElse(null);
			ClassContract classContract = iClassContractRepository.findById(suspension.getClassContractId()).orElse(null);
			if(student != null && classContract != null) {
				StudentClassSuspensionDto suspensionDto = new StudentClassSuspensionDto();
				suspensionDto.setClassContractId(classContract.getId());
				suspensionDto.setStudentClassId(suspension.getId());
				suspensionDto.setStudentName(student.getFirstName()+" " +student.getLastName());
				suspensionDto.setStudentId(student.getId());
				suspensionDto.setClassDetails(classContract.getSubject()+" "+ classContract.getMedium() + " " + classContract.getGrade());
				suspensionDto.setContactNumber(student.getContactNumber());
				suspensionDto.setGuardianSuspended(student.getGuardian() == null ? 0 : student.getGuardian().getSuspended());
				suspensionDtos.add(suspensionDto);
			}
		}
		return suspensionDtos;
	}
	public List<StudentClassDto> getStudentsInClass(Long classId){
		List<StudentClassDto> studentClassDtos = new ArrayList<>();
		List<StudentClass> studentsInClass = iStudentClassRepository.findByClassContractId(classId);
		List<Long> studentIds = studentsInClass.stream().map(student -> student.getStudentId()).collect(Collectors.toList());
		if(!studentIds.isEmpty()) {
			List<Student> students = iStudentRepository.findAllById(studentIds);
			for (Student student: students) {
				StudentClassDto dto = new StudentClassDto();
				dto.setClassId(classId);
				dto.setStudentId(student.getId());
				dto.setStudentName(student.getFirstName()+ " " + student.getLastName());
				studentClassDtos.add(dto);
			}
		}
		return studentClassDtos;
	}
	
	public List<PendingAssignmentDto> getPendingAssignmentsForStudent(List<Student> studentIds) {
		List<PendingAssignmentDto> pendingAssignments = new ArrayList<>();
		System.out.println("logs for pending assignments - start");
		System.out.println("number of students: " + studentIds.size());
		for (Student studentId : studentIds) {
			List<StudentClass> studentClasses = iStudentClassRepository.findByStudentId(studentId.getId());
			List<Long> studentClassIds = studentClasses.stream().map(studentClass -> studentClass.getClassContractId()).collect(Collectors.toList());
			List<Assignment> assignments = iAssignmentRepository.findByClassIdIn(studentClassIds);
			System.out.println("number of classes for student :" + studentClasses.size());
			System.out.println("number of assignments in classes :" + assignments.size());
			if (!assignments.isEmpty()) {
				for (Assignment assignment : assignments) {
					AssignmentSubmission assignmentSubmmission = iAssignmentSubmissionRepository.findFirstByAssignmentIdAndStudentId(assignment.getId(), studentId.getId());
					ClassContract classContract = iClassContractRepository.findById(assignment.getClassId()).orElse(null);
					System.out.println("assignment submitted ?" + assignmentSubmmission != null);
					if (assignmentSubmmission == null && classContract != null) {
						PendingAssignmentDto pendingAssignment = new PendingAssignmentDto();
						pendingAssignment.setClassContractId(classContract.getId());
						pendingAssignment.setClassName(classContract.getSubject() +" Grade " + classContract.getGrade());
						pendingAssignment.setAssignmentId(assignment.getId());
						pendingAssignment.setDueDate(assignment.getDueDate());
						pendingAssignment.setAssignmentName(assignment.getName());
						pendingAssignment.setStudentId(studentId.getId());
						pendingAssignment.setStudentName(studentId.getFirstName() + " " + studentId.getLastName());
						pendingAssignments.add(pendingAssignment);
					}
				}
				
			}
		}
		System.out.println("logs for pending assignments - end");
		return pendingAssignments;
	}
	
	public void unEnrollStudentFromClass(StudentClassDto dto) throws Exception {
		
			System.out.println("here");
			System.out.println(dto.getStudentId());
			System.out.println(dto.getClassId());
			StudentClass studentClass = iStudentClassRepository.findFirstByStudentIdAndClassContractId(dto.getStudentId(), dto.getClassId());
			Student student = iStudentRepository.findById(dto.getStudentId()).orElse(null);
			if (student != null) {
				//Add students to unEnrolledStudent table
				UnenrolledStudent unenrolledStudent = new UnenrolledStudent();
				unenrolledStudent.setStudentId(dto.getStudentId());
				unenrolledStudent.setClassContractId(dto.getClassId());
				unenrolledStudent.setStudentFirstName(student.getFirstName());
				unenrolledStudent.setStudentLastName(student.getLastName());
				unenrolledStudent.setContactNumber(student.getGuardian().getContactNumber());
				iUnenrolledStudentRepository.save(unenrolledStudent);
			}
			
			iStudentClassRepository.delete(studentClass);
		
		
	}
}
