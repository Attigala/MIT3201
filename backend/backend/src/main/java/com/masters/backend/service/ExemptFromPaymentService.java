package com.masters.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.masters.backend.dto.ExemptionStatistics;
import com.masters.backend.dto.ExemptionSummaryDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ExemptFromPayment;
import com.masters.backend.model.Guardian;
import com.masters.backend.model.Student;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.ExemptFromPaymentRepository;
import com.masters.backend.repository.GuardianRepository;
import com.masters.backend.repository.StudentRepository;

@Service
public class ExemptFromPaymentService {
	private ExemptFromPaymentRepository iExemptFromPaymentRepository;
	private StudentRepository iStudentRepository;
	private GuardianRepository iGuardianRepository;
	private ClassContractRepository iClassContractRepository;
	
	public List<ExemptionStatistics> compileStatistics(){
		List<ExemptionStatistics> exemptions = new ArrayList<ExemptionStatistics>();
		
		List<ExemptFromPayment> exemptionFromPayments = iExemptFromPaymentRepository.findAll();
		
		for(ExemptFromPayment exempt : exemptionFromPayments) {
			ExemptFromPayment forCount = new ExemptFromPayment();
			forCount.setClassContractId(exempt.getClassContractId());
			Student student = iStudentRepository.findById(exempt.getStudentId()).orElse(null);
			Guardian guardian = student == null ? null : student.getGuardian();
			ClassContract classContract = iClassContractRepository.findById(exempt.getClassContractId()).orElse(null);
			ExemptionSummaryDto summaryDto = new ExemptionSummaryDto();
			summaryDto.setClassId(exempt.getClassContractId());
			ExemptionStatistics exemption;
			if (student != null && classContract != null) {
				exemption = new ExemptionStatistics();
				summaryDto.setClassName(classContract.getSubject() + "-" + classContract.getMedium() + "-" + classContract.getGrade());
				exemption.setStudent(student.getFirstName()+" "+student.getLastName());
				exemption.setGuardian(guardian.getFirstName() + " " + guardian.getLastName());
				exemption.setGrade(classContract.getGrade().toString());
				exemption.setMedium(classContract.getMedium());
				exemption.setSubject(classContract.getSubject());
				exemption.setAddedBy(exempt.getAddedBy());
				exemption.setAddedDate(exemption.getAddedDate());
				//TODO: Continue with summary create an arraylist add to that array list
				exemptions.add(exemption);
			}
		}
		//if exeptions is not empty add the summary before returning
		return exemptions;
		
	}
}
