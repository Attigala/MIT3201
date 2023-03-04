package com.masters.backend.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.masters.backend.dto.NewPaymentDto;
import com.masters.backend.dto.PaymentReceiptDto;
import com.masters.backend.dto.StudentMarkDto;
import com.masters.backend.model.Assignment;
import com.masters.backend.model.AssignmentMark;
import com.masters.backend.model.AssignmentSubmission;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.Guardian;
import com.masters.backend.model.PaymentReceipt;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudentClass;
import com.masters.backend.model.StudyMaterial;
import com.masters.backend.repository.AssignmentMarkRepository;
import com.masters.backend.repository.AssignmentRepository;
import com.masters.backend.repository.AssignmentSubmissionRepository;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.NewReciept;
import com.masters.backend.repository.PaymentReceiptRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.StudyMaterialRepository;




@Service
public class FileHandlingService {
	@Autowired StudyMaterialRepository iStudyMaterialRepository;
	@Autowired AssignmentRepository iAssignmentRepository;
	@Autowired PaymentReceiptRepository iPaymentReceiptRepository;
	@Autowired StudentRepository iStudentRepository;
	@Autowired GuardianRepository iGuardianRepository;
	@Autowired ClassContractRepository iClassContractRepository;
	@Autowired AssignmentSubmissionRepository iAssignmentSubmissionRepository;
	@Autowired StudentClassRepository iStudentClassRepository;
	@Autowired AssignmentMarkRepository iAssignmentMarkRepository;
	
	private static final String SUBMISSION_STATUS_LATE = "LATE";
	private static final String SUBMISSION_STATUS_OK = "OK";
	
	public StudyMaterial store(Long classId, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		StudyMaterial studyMaterail = new StudyMaterial();
		studyMaterail.setClassId(classId);
		studyMaterail.setName(fileName);
		studyMaterail.setType(file.getContentType());
		studyMaterail.setData(file.getBytes());
		
		return iStudyMaterialRepository.save(studyMaterail);
	}
	
	public Stream<StudyMaterial> getAllFiles(Long classId){
		return iStudyMaterialRepository.findByClassId(classId).stream();
	}
	
	public StudyMaterial getFile(Long id) {
		return iStudyMaterialRepository.findById(id).get();
	}
	
	public Assignment storeAssignment(Long classId, Date date, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Assignment assignment = new Assignment();
		assignment.setClassId(classId);
		assignment.setDueDate(date);
		assignment.setData(file.getBytes());
		assignment.setName(fileName);
		assignment.setType(file.getContentType());
		
		return iAssignmentRepository.save(assignment);
	}
	public Assignment getAssignment(Long id) {
		return iAssignmentRepository.findById(id).get();
	}
	
	public Stream<Assignment> getAllAssignments(Long classId) {
		return iAssignmentRepository.findByClassId(classId).stream();
	}
	
	public Stream<Assignment> getAllBeforeDueDate(Long classId) {
		return iAssignmentRepository.findByClassIdAndDueDateAfter(classId, new Date(System.currentTimeMillis())).stream();
	}
	
	public PaymentReceipt storePaymentReceipt (Long studentId, Long ClassId, String month, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		PaymentReceipt reciept = new PaymentReceipt();
		Student student = iStudentRepository.findById(studentId).orElse(null);
		if (student != null) {
			Guardian guardian = student.getGuardian();
			reciept.setClassContractId(ClassId);
			reciept.setGuardianId(guardian.getId());
			reciept.setStudentId(student.getId());
			reciept.setMonth(month);
			reciept.setYear(LocalDate.now().getYear());
			reciept.setStatus("NEW");
			reciept.setPaymentDate(new Date(Calendar.getInstance().getTime().getTime()));
			reciept.setType(file.getContentType());
			reciept.setFileName(fileName);
			reciept.setData(file.getBytes());
			iPaymentReceiptRepository.save(reciept);
			return reciept;
		}
		return null;
	}
	
	public List<NewPaymentDto> getAllNewReciepts(){
		List<PaymentReceipt> newReciepts = iPaymentReceiptRepository.findByStatusAndYear("NEW", LocalDate.now().getYear());
		List<NewPaymentDto> newPaymentDtos = new ArrayList<NewPaymentDto>();
		for(PaymentReceipt reciepts : newReciepts) {
			Student student = iStudentRepository.findById(reciepts.getStudentId()).orElse(null);
			Guardian guardian = iGuardianRepository.findById(reciepts.getGuardianId()).orElse(null);
			ClassContract classContract = iClassContractRepository.findById(reciepts.getClassContractId()).orElse(null);
			NewPaymentDto newPaymentDto = new NewPaymentDto();
			newPaymentDto.setId(reciepts.getId());
			newPaymentDto.setClassName(classContract != null ? classContract.getSubject() + classContract.getMedium() + classContract.getGrade() : "Empty");
			newPaymentDto.setStudent(student != null ? student.getFirstName() + " " + student.getLastName() : "Empty");
			newPaymentDto.setGuardian(guardian != null ? guardian.getFirstName()+" "+guardian.getLastName() : "Empty");
			newPaymentDto.setMonth(reciepts.getMonth());
			newPaymentDto.setPaymentDate(reciepts.getPaymentDate());
			newPaymentDtos.add(newPaymentDto);
		}
		return newPaymentDtos;
	}
	
	public PaymentReceipt getReceipt(Long id) {
		return iPaymentReceiptRepository.findById(id).get();
	}
	
	public PaymentReceipt changeStatus(Long id, String status) {
		System.out.println(id);
		System.out.println(status);
		PaymentReceipt receipt = iPaymentReceiptRepository.findById(id).orElse(null);
		
		if (receipt != null) {
			receipt.setStatus(status);
			iPaymentReceiptRepository.save(receipt);
		}
		return receipt;
	}
	
	public List<NewPaymentDto> getRejectedReceipts(String email) {
		Guardian guardian = iGuardianRepository.findFirstByEmail(email);
		List<PaymentReceipt> paymentReceipts = new ArrayList<PaymentReceipt>();
		List<NewPaymentDto> rejectedPayments = new ArrayList<NewPaymentDto>();
		if (guardian != null) {
			paymentReceipts = iPaymentReceiptRepository.findByGuardianIdAndStatus(guardian.getId(), "REJECTED");
			for (PaymentReceipt payment : paymentReceipts) {
				Student student = iStudentRepository.findById(payment.getStudentId()).orElse(null);
				ClassContract classContract = iClassContractRepository.findById(payment.getClassContractId()).orElse(null);
				NewPaymentDto newPaymentDto = new NewPaymentDto();
				newPaymentDto.setId(payment.getId());
				newPaymentDto.setClassName(classContract != null ? classContract.getSubject() + classContract.getMedium() + classContract.getGrade() : "Empty");
				newPaymentDto.setStudent(student != null ? student.getFirstName() + " " + student.getLastName() : "Empty");
				newPaymentDto.setGuardian(guardian != null ? guardian.getFirstName()+" "+guardian.getLastName() : "Empty");
				newPaymentDto.setMonth(payment.getMonth());
				newPaymentDto.setPaymentDate(payment.getPaymentDate());
				rejectedPayments.add(newPaymentDto);				
			}
		}
		return rejectedPayments;
	}
	
	public AssignmentSubmission storeCompletedAssignments (Long studentId, Long ClassId, Long assignmentId, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		AssignmentSubmission assignmentSubmission = iAssignmentSubmissionRepository.findFirstByAssignmentIdAndStudentId(assignmentId, studentId);
		Assignment assignment = iAssignmentRepository.findById(assignmentId).orElse(null);
		if(assignmentSubmission == null) {
			assignmentSubmission = new AssignmentSubmission();				
		}
		Student student = iStudentRepository.findById(studentId).orElse(null);
		if (student != null) {
			assignmentSubmission.setAssignmentId(assignmentId);
			assignmentSubmission.setClassId(ClassId);
			assignmentSubmission.setStudentId(studentId);
			assignmentSubmission.setUploadedDate(new Date(Calendar.getInstance().getTime().getTime()));
			assignmentSubmission.setType(file.getContentType());
			assignmentSubmission.setName(fileName);
			assignmentSubmission.setData(file.getBytes());
			if (assignment != null && assignment.getDueDate().before(new Date(Calendar.getInstance().getTime().getTime()))) {
				assignmentSubmission.setStatus(SUBMISSION_STATUS_LATE);
			} else {
				assignmentSubmission.setStatus(SUBMISSION_STATUS_OK);
			}
			iAssignmentSubmissionRepository.save(assignmentSubmission);
			return assignmentSubmission;
		}
		return null;
	}
	
	public AssignmentSubmission getAssignmentSubmission(Long id) {
		return iAssignmentSubmissionRepository.findById(id).orElse(null);
	}
	
	public Stream<AssignmentSubmission> getAllAssignmentSubmissions(Long assignmentId) {
		return iAssignmentSubmissionRepository.findByAssignmentId(assignmentId).stream();
	}
	
	public AssignmentSubmission getAssignmentSubmissionforStudent(Long assignmentId, Long studentId) {
		return iAssignmentSubmissionRepository.findFirstByAssignmentIdAndStudentId(assignmentId, studentId);
	}
	
	public List<Student> getNotSubmittedStudents(Long assignmentId) {
		List<Long> submittedStudents = getAllAssignmentSubmissions(assignmentId)
				.map(assignments -> assignments.getStudentId()).collect(Collectors.toList());
		Assignment assignment = iAssignmentRepository.findById(assignmentId).orElse(null);
		if (assignment != null) {
			List<Long> otherStudents = iStudentClassRepository
					.findByStudentIdNotInAndClassContractIdInAndSuspended(submittedStudents, Arrays.asList(assignment.getClassId()), 0)
					.stream().map(assignments -> assignments.getStudentId()).collect(Collectors.toList());
			return iStudentRepository.findAllById(otherStudents);
		}
		return null;
	}
	
	public List<Student> getLateSubmissions(Long assignmentId) {
		List<Long> lateSubmissionStudentIds = iAssignmentSubmissionRepository
				.findByAssignmentIdAndStatus(assignmentId, SUBMISSION_STATUS_LATE)
				.stream()
				.map(assignments -> assignments.getStudentId()).collect(Collectors.toList());
		if (lateSubmissionStudentIds != null) {
			return iStudentRepository.findAllById(lateSubmissionStudentIds);
		}
		return null;
	}
	
	public List<StudentMarkDto> getMarksForAssignment(Long assignmentId) {
		List<StudentMarkDto> studentMarks = new ArrayList<>();
		List<AssignmentMark> assignmentMarks = iAssignmentMarkRepository.findByAssignmentId(assignmentId);
		for(AssignmentMark assignmentMark: assignmentMarks) {
			Student student = iStudentRepository.findById(assignmentMark.getStudentId()).orElse(null);
			if(student != null) {
				StudentMarkDto studentMarkDto = new StudentMarkDto();
				studentMarkDto.setAssignmentId(assignmentId);
				studentMarkDto.setClassContractId(assignmentMark.getClassContractId());
				studentMarkDto.setStudentId(student.getId());
				studentMarkDto.setStudentName(student.getFirstName()+" "+student.getLastName());
				studentMarkDto.setMark(assignmentMark.getMark());
				studentMarkDto.setAssignmentMarkId(assignmentMark.getId());
				studentMarks.add(studentMarkDto);
			}
		}
		return studentMarks;
	}
	
	public List<StudentMarkDto> getMarksForStudent(Long studentId) {
		List<StudentMarkDto> studentMarks = new ArrayList<>();
		List<AssignmentMark> assignmentMarks = iAssignmentMarkRepository.findByStudentId(studentId);
		for(AssignmentMark assignmentMark: assignmentMarks) {
			Student student = iStudentRepository.findById(assignmentMark.getStudentId()).orElse(null);
			ClassContract classContract = iClassContractRepository.findById(assignmentMark.getClassContractId()).orElse(null);
			if(student != null && classContract != null) {
				StudentMarkDto studentMarkDto = new StudentMarkDto();
				studentMarkDto.setAssignmentId(assignmentMark.getAssignmentId());
				studentMarkDto.setClassContractId(assignmentMark.getClassContractId());
				studentMarkDto.setStudentId(student.getId());
				studentMarkDto.setStudentName(student.getFirstName()+" "+student.getLastName());
				studentMarkDto.setMark(assignmentMark.getMark());
				studentMarkDto.setAssignmentMarkId(assignmentMark.getId());
				studentMarkDto.setClassContractName(classContract.getSubject() + " " + classContract.getMedium() + " " + classContract.getGrade());
				studentMarks.add(studentMarkDto);
			}
		}
		System.out.println(studentMarks.size());
		return studentMarks;
	}
	
	public AssignmentSubmission getMarksSheet(Long id) {
		AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
		Assignment assignment = iAssignmentRepository.findById(id).orElse(null);
		String header[] = new String[] {"classId", "assignmentId", "studentId", "studentName", "marks"};
		List<String[]> dataLines = new ArrayList<>();
		dataLines.add(header);
		if (assignment != null) {
			List<StudentClass> studentClasses = iStudentClassRepository.findByClassContractId(assignment.getClassId());
			for (StudentClass studentClass : studentClasses) {
				Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);
				if (student != null) {
					String studentData[] = new String[] { studentClass.getClassContractId().toString(), id.toString(),
							student.getId().toString(), student.getFirstName() + " " + student.getLastName() };
					dataLines.add(studentData);
				}
			}
		}
		File csvOutputFile = new File("marks_sheet_"+id+".csv");
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	        
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    assignmentSubmission.setName(csvOutputFile.getName());
        try {
			assignmentSubmission.setData(Files.readAllBytes(csvOutputFile.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return assignmentSubmission;
	}
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	
	public List<AssignmentMark> addMarksFromCsv(MultipartFile file) {
		List<AssignmentMark> assignmentMarks = new ArrayList<AssignmentMark>();
		String DELIMITER = ",";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new java.io.InputStreamReader(file.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				// convert line into tokens
		        String[] tokens = line.split(DELIMITER);
		        if(tokens[0].equals("classId") || tokens.length < 5) {
		        	continue;
		        }

		        AssignmentMark mark = new AssignmentMark();
		        mark.setClassContractId(Long.parseLong(tokens[0]));
		        mark.setAssignmentId(Long.parseLong(tokens[1]));
		        mark.setStudentId(Long.parseLong(tokens[2]));
		        mark = iAssignmentMarkRepository.findOne(Example.of(mark)).orElse(mark);
		        mark.setMark(Integer.parseInt(tokens[4]));
		        iAssignmentMarkRepository.save(mark);
		        assignmentMarks.add(mark);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assignmentMarks;
	}
}

