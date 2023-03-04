package com.masters.backend.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.UnpaidDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ClassPayment;
import com.masters.backend.model.ExemptFromPayment;
import com.masters.backend.model.Guardian;
import com.masters.backend.model.PaymentReceipt;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudentClass;
import com.masters.backend.model.Teacher;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.ClassPaymentRepository;
import com.masters.backend.repository.ExemptFromPaymentRepository;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.PaymentReceiptRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.TeacherRepository;

@Service
public class ClassPaymentService {
	
	@Autowired private StudentClassRepository iStudentClassRepository;
	@Autowired private ClassPaymentRepository iClassPaymentRepository;
	@Autowired private StudentRepository iStudentRepository;
	@Autowired private ClassContractRepository iClassContractRepository;
	@Autowired private TeacherRepository iTeacherRepository;
	@Autowired private GuardianRepository iGuardianRepository;
	@Autowired private PaymentReceiptRepository iPaymentReceiptRepository;
	@Autowired private ExemptFromPaymentRepository iExemptFromPaymentRepository;
	
	public ClassPayment createPayment(ClassPayment payment) {
		StudentClass studentClass = new StudentClass();
		studentClass.setStudentId(payment.getStudentId());
		studentClass.setClassContractId(payment.getClassContractId());
		Optional<StudentClass> registeredStudent = iStudentClassRepository.findOne(Example.of(studentClass));
		if (registeredStudent == null ) {
			return null;
		} else {
			return iClassPaymentRepository.save(payment);
		}
	}
	
	public List<UnpaidDto> getUnpaidStudentList() {
		System.out.println("inside getunpaidstudentlist");
		ClassPayment paymentExample = new ClassPayment();
		paymentExample.setMonth(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
		paymentExample.setYear(LocalDate.now().getYear());
		List<UnpaidDto> unpaidDtos = new ArrayList<UnpaidDto>();
		List<ClassPayment> classPayments = iClassPaymentRepository.findAll(Example.of(paymentExample));
		System.out.println(classPayments.size());
		System.out.println(classPayments != null || classPayments.size()>1);
		if (classPayments != null && classPayments.size()>=1) {
			List<StudentClass> studentClasses = iStudentClassRepository.findByStudentIdNotIn(classPayments.stream()
					.map(classPayment -> classPayment.getStudentId()).collect(Collectors.toList()));
			System.out.println(studentClasses.size());
			if (studentClasses != null && studentClasses.size()>0) {
				for (StudentClass studentClass : studentClasses) {
					UnpaidDto unpaidDto = new UnpaidDto();
					ClassContract classContract = iClassContractRepository.findById(studentClass.getClassContractId())
							.orElse(null);
					Teacher teacher = classContract.getTeacher();
					Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);
					unpaidDto.setTeacher(teacher == null ? null : teacher.getFirstName() + " " + teacher.getLastName());
					unpaidDto.setClassDetails(classContract == null ? null
							: classContract.getMedium() + "-" + classContract.getSubject() + "-"
									+ classContract.getGrade());
					unpaidDto.setStudentName(
							student == null ? null : student.getFirstName() + " " + student.getLastName());
					unpaidDto.setStudentContactNumber(student == null || student.getGuardian() == null ? null
							: student.getGuardian().getContactNumber());
					unpaidDto.setGuardianName(student == null || student.getGuardian() == null ? null
							: student.getGuardian().getFirstName() + " " + student.getLastName());
					unpaidDtos.add(unpaidDto);
				}
				return unpaidDtos;
			}
			return null;
		} else {
			System.out.println("inside else");
			List<StudentClass> studentClasses = iStudentClassRepository.findAll();
			
			System.out.println(studentClasses.size());
			if (studentClasses != null) {
				for (StudentClass studentClass : studentClasses) {
					System.out.println("inside for loop");
					UnpaidDto unpaidDto = new UnpaidDto();
					ClassContract classContract = iClassContractRepository.findById(studentClass.getClassContractId())
							.orElse(null);
					Teacher teacher = classContract.getTeacher();
					Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);
					unpaidDto.setTeacher(teacher == null ? null : teacher.getFirstName() + " " + teacher.getLastName());
					unpaidDto.setClassDetails(classContract == null ? null
							: classContract.getMedium() + "-" + classContract.getSubject() + "-"
									+ classContract.getGrade());
					unpaidDto.setStudentName(
							student == null ? null : student.getFirstName() + " " + student.getLastName());
					unpaidDto.setStudentContactNumber(student == null || student.getGuardian() == null ? null
							: student.getGuardian().getContactNumber());
					unpaidDto.setGuardianName(student == null || student.getGuardian() == null ? null
							: student.getGuardian().getFirstName() + " " + student.getLastName());
					unpaidDtos.add(unpaidDto);
					System.out.println(unpaidDtos.size());
				}
			    return unpaidDtos;
			}
			return null;
		}
	}
	
	public List<ClassPayment> findByMonthAndYear(String month, Integer year) {
		return iClassPaymentRepository.findByMonthAndYear(month, year);
	}
	
	public List<UnpaidDto> unpaidForGuardian(String email) {
		Guardian guardian = iGuardianRepository.findFirstByEmail(email);
		List<UnpaidDto> unpaids = new ArrayList<>();
		if (guardian != null) {
			List<Long> students = iStudentRepository.findByGuardian(guardian).stream().map(student -> student.getId())
					.collect(Collectors.toList());

			List<PaymentReceipt> paidClasses = iPaymentReceiptRepository.findByMonthAndYearAndStudentIdIn(
					LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
					Calendar.getInstance().get(Calendar.YEAR), students);
			if (paidClasses.isEmpty()) {
				List<StudentClass> classesForStudent = iStudentClassRepository.findByStudentIdIn(students);
				for(StudentClass studentClass: classesForStudent) {
					UnpaidDto unpaidDto = new UnpaidDto();
					Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);
					ClassContract classContract = iClassContractRepository.findById(studentClass.getClassContractId()).orElse(null);
					if(classContract != null && student != null) {
						unpaidDto.setClassDetails(classContract.getSubject()+ " " + classContract.getMedium()+ " " +classContract.getGrade());
						unpaidDto.setStudentName(student.getFirstName() + " "+ student.getLastName());
						unpaids.add(unpaidDto);
					}
				}
			} else {
				List<Long> paidClassIds = paidClasses.stream().map(paid -> paid.getClassContractId()).collect(Collectors.toList());
				List<StudentClass> unpaidClasses = iStudentClassRepository.findByStudentIdInAndClassContractIdNotInAndSuspended(students, paidClassIds, 0);
				for(StudentClass studentClass: unpaidClasses) {
					UnpaidDto unpaidDto = new UnpaidDto();
					Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);
					ClassContract classContract = iClassContractRepository.findById(studentClass.getClassContractId()).orElse(null);
					if(classContract != null && student != null) {
						unpaidDto.setClassDetails(classContract.getSubject()+ " " + classContract.getMedium()+ " " +classContract.getGrade());
						unpaidDto.setStudentName(student.getFirstName() + " "+ student.getLastName());
						unpaids.add(unpaidDto);
					}
				}
			}

		}
		return unpaids;
	}
	
	public List<UnpaidDto> unpaidThisMonth() {
		List<UnpaidDto> unpaidDtos = new ArrayList<>();
		List<PaymentReceipt> paymentReciepts = iPaymentReceiptRepository.findByMonthAndYear(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
				Calendar.getInstance().get(Calendar.YEAR));
		List<StudentClass> paidClasses = new ArrayList<>();
		for(PaymentReceipt paymentReceipt : paymentReciepts) {
			StudentClass studentClass = iStudentClassRepository.findFirstByStudentIdAndClassContractId(paymentReceipt.getStudentId(), paymentReceipt.getClassContractId());
			if (studentClass != null) {
				paidClasses.add(studentClass);
			}

		}
		List<ExemptFromPayment> exemptions = iExemptFromPaymentRepository.findAll();
		for(ExemptFromPayment exemption: exemptions) {
			StudentClass studentClass = iStudentClassRepository.findFirstByStudentIdAndClassContractId(exemption.getStudentId(), exemption.getClassContractId());
			if (studentClass != null) {
				paidClasses.add(studentClass);
			}
		}
		if(!paidClasses.isEmpty()) {
			List<StudentClass> unpaidClasses = iStudentClassRepository.findByIdNotIn(paidClasses.stream().map(paid -> paid.getId()).collect(Collectors.toList()));
			for(StudentClass unpaidClass : unpaidClasses) {
				UnpaidDto unpaidDto = new UnpaidDto();
				Student student = iStudentRepository.findById(unpaidClass.getStudentId()).orElse(null);
				ClassContract classContract = iClassContractRepository.findById(unpaidClass.getClassContractId()).orElse(null);
				if(student != null && classContract != null) {
					Guardian guardian = student.getGuardian();
					unpaidDto.setGuardianId(guardian.getId());
					unpaidDto.setGuardianName(guardian.getFirstName() + " " + guardian.getLastName());
					unpaidDto.setClassDetails(classContract.getSubject()+" "+ classContract.getMedium() + " " + classContract.getGrade());
					unpaidDto.setStudentContactNumber(guardian.getContactNumber());
					unpaidDto.setStudentName(student.getFirstName() + " "+ student.getLastName());
					unpaidDto.setStudentId(student.getId());
					unpaidDto.setClassContractId(classContract.getId());
					if(guardian.getSuspended() == 1 || student.getSuspended() == 1) {
						unpaidDto.setSuspended(1);
					}
					unpaidDtos.add(unpaidDto);
				}
			}
		}
		return unpaidDtos;
		
	}

}
