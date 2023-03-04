package com.masters.backend.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.loader.custom.CollectionFetchReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.masters.backend.dto.AssignmentSubmissionDto;
import com.masters.backend.dto.NewPaymentDto;
import com.masters.backend.dto.PaymentReceiptDto;
import com.masters.backend.dto.StudentMarkDto;
import com.masters.backend.dto.StudyMaterialDto;
import com.masters.backend.model.Assignment;
import com.masters.backend.model.AssignmentMark;
import com.masters.backend.model.AssignmentSubmission;
import com.masters.backend.model.PaymentReceipt;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudyMaterial;
import com.masters.backend.repository.AssignmentMarkRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.service.FileHandlingService;

@RestController
public class StudyMaterialController {
	
	@Autowired FileHandlingService iFileHandlingService;
	@Autowired StudentRepository iStudentRepository;
	@Autowired AssignmentMarkRepository iAssignmentMarkRepository;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/studyMaterial/upload")
	public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file, @RequestParam("classId") Long classId) {
		try {
			iFileHandlingService.store(classId, file);
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File could not be uploaded, try again or contact administrator");
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/studyMaterial/all")
	public List<StudyMaterialDto> getAllFilesForClass(@RequestParam ("classId") Long classId) {
		List<StudyMaterialDto> files = iFileHandlingService.getAllFiles(classId).map(studyMaterial -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/studyMaterial/all/").path(studyMaterial.getId().toString()).toUriString();
			StudyMaterialDto dto = new StudyMaterialDto();
			dto.setName(studyMaterial.getName());
			dto.setClassId(studyMaterial.getClassId());
			dto.setType(studyMaterial.getType());
			dto.setSize(studyMaterial.getData().length);
			dto.setUrl(fileDownloadUri);
			return dto;
		}).collect(Collectors.toList());
		
		return files;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/studyMaterial/all/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		StudyMaterial studyMaterial = iFileHandlingService.getFile(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+studyMaterial.getName()+"\"").body(studyMaterial.getData());
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/assignment/upload")
	public ResponseEntity<String> uploadAssignment(@RequestBody MultipartFile file, @RequestParam("classId") Long classId, @RequestParam("date") Date date) {
		try {
			iFileHandlingService.storeAssignment(classId, date, file);
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File could not be uploaded, try again or contact administrator");
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignment/all")
	public List<StudyMaterialDto> getAllAssignmentsForClass(@RequestParam ("classId") Long classId) {
		List<StudyMaterialDto> files = iFileHandlingService.getAllAssignments(classId).map(studyMaterial -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/assignment/all/").path(studyMaterial.getId().toString()).toUriString();
			String markSheetDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/marksSheet/assignment/").path(studyMaterial.getId().toString()).toUriString();
			StudyMaterialDto dto = new StudyMaterialDto();
			dto.setId(studyMaterial.getId());
			dto.setName(studyMaterial.getName());
			dto.setClassId(studyMaterial.getClassId());
			dto.setType(studyMaterial.getType());
			dto.setSize(studyMaterial.getData().length);
			dto.setUrl(fileDownloadUri);
			dto.setDueDate(studyMaterial.getDueDate());
			dto.setMarkSheetUrl(markSheetDownloadUri);
			return dto;
		}).collect(Collectors.toList());
		
		return files;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignment/all/{id}")
	public ResponseEntity<byte[]> getAssignment(@PathVariable Long id) {
		Assignment studyMaterial = iFileHandlingService.getAssignment(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+studyMaterial.getName()+"\"").body(studyMaterial.getData());
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/reciept/upload")
	public ResponseEntity<String> uploadReciept(@RequestBody MultipartFile file, @RequestParam ("classId") Long classId, @RequestParam ("studentId") Long studentId, @RequestParam("month") String month) {
		try {
			iFileHandlingService.storePaymentReceipt(studentId, classId,month, file );
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File could not be uploaded, try again or contact administrator");
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/reciept/all/new")
	public List<NewPaymentDto> getNewPayments(){
		List<NewPaymentDto> payments= iFileHandlingService.getAllNewReciepts();
		payments.forEach(payment -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/reciept/all/").path(payment.getId().toString()).toUriString();
			payment.setUrl(fileDownloadUri);
		});
		
		return payments;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/reciept/all/{id}")
	public ResponseEntity<byte[]> getReciept(@PathVariable Long id) {
		PaymentReceipt paymentReceipt = iFileHandlingService.getReceipt(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+paymentReceipt.getFileName()+"\"").body(paymentReceipt.getData());
	}


	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/reciept/update")
	public PaymentReceipt updateReciept (@RequestParam("id") Long id, @RequestParam ("status") String status) {
		return iFileHandlingService.changeStatus(id, status);
	}

	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/reciept/all/rejected")
	public List<NewPaymentDto> getRejectedPayments(@RequestParam("email") String email) {
		return iFileHandlingService.getRejectedReceipts(email);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/assignmentSubmission/upload")
	public ResponseEntity<String> uploadSubmission(@RequestBody MultipartFile file, @RequestParam ("classId") Long classId, @RequestParam ("studentId") Long studentId, @RequestParam("assignmentId") Long assignmentId) {
		try {
			iFileHandlingService.storeCompletedAssignments(studentId, classId,assignmentId, file );
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File could not be uploaded, try again or contact administrator");
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/all")
	public List<AssignmentSubmissionDto> getAssignmentSubmissionForAssignment(@RequestParam ("assignmentId") Long assignmentId) {
		List<AssignmentSubmissionDto> files = iFileHandlingService.getAllAssignmentSubmissions(assignmentId).map(assignmentSubmission -> {
			AssignmentSubmissionDto dto = new AssignmentSubmissionDto();
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/assignmentSubmission/all/").path(assignmentSubmission.getId().toString()).toUriString();
			dto.setClassId(assignmentSubmission.getClassId());
			dto.setAssignmentId(assignmentId);
			dto.setStudentId(assignmentSubmission.getStudentId());
			dto.setName(assignmentSubmission.getName());
			dto.setSize(assignmentSubmission.getData().length);
			dto.setType(assignmentSubmission.getType());
			dto.setUploadedDate(assignmentSubmission.getUploadedDate());
			dto.setUrl(fileDownloadUri);
			Student student = iStudentRepository.findById(assignmentSubmission.getStudentId()).orElse(null);
			if (student != null) {
				dto.setStudentName(student.getFirstName() + " " + student.getLastName() );
			}
			return dto;
		}).collect(Collectors.toList());
		return files;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/all/{id}")
	public ResponseEntity<byte[]> getAssignmentSubmission(@PathVariable Long id) {
		AssignmentSubmission assignmentSubmission = iFileHandlingService.getAssignmentSubmission(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+assignmentSubmission.getName()+"\"").body(assignmentSubmission.getData());
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/submitted")
	public AssignmentSubmission getSumittedAssignmentForStudent(@RequestParam ("studentId") Long studentId, @RequestParam("assignmentId") Long assignmentId) {
		return iFileHandlingService.getAssignmentSubmissionforStudent(assignmentId, studentId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/not")
	public List<Student> getNoSubmissionStudents(@RequestParam("assignmentId") Long assignmentId) {
		return iFileHandlingService.getNotSubmittedStudents(assignmentId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/late")
	public List<Student> getLateSubmissionStudents(@RequestParam("assignmentId") Long assignmentId) {
		return iFileHandlingService.getLateSubmissions(assignmentId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/assignentSubmission/mark")
	public AssignmentMark saveMark(@RequestBody AssignmentMark assignmentMark) {
		return iAssignmentMarkRepository.save(assignmentMark);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/mark/all")
	public List<StudentMarkDto> getMarksForAssignment(@RequestParam(name="assignmentId", required=true) Long assignmentId){
		return iFileHandlingService.getMarksForAssignment(assignmentId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/assignmentSubmission/mark/student")
	public List<StudentMarkDto> getMarksForStudent(@RequestParam(name="email", required=true) String email){
		System.out.println(email);
		Student student = iStudentRepository.findFirstByEmail(email);
		if(student==null) {
			System.out.println("student not there ");
			return null;
		}
		return iFileHandlingService.getMarksForStudent(student.getId());
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/marksSheet/assignment/{id}")
	public ResponseEntity<byte[]> getMarksSheet(@PathVariable Long id) {
		AssignmentSubmission assignmentSubmission = iFileHandlingService.getMarksSheet(id);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+assignmentSubmission.getName()+"\"").body(assignmentSubmission.getData());
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/assignment/marks/upload")
	public List<AssignmentMark> uploadMarks(@RequestBody MultipartFile file) {
		return iFileHandlingService.addMarksFromCsv(file);
	}
}
