package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masters.backend.model.PaymentReceipt;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {
	
	List<PaymentReceipt> findByStatus(String status);

	List<PaymentReceipt> findByStatusAndYear(String status, Integer year);
	
	List<PaymentReceipt> findByGuardianIdAndStatus(Long guardianId, String status);
	
	List<PaymentReceipt> findByMonthAndYearAndStudentIdIn (String month, Integer year, List<Long> studentId);
	
	List<PaymentReceipt> findByMonthAndYear(String month, Integer year);

}
