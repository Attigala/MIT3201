package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.ClassPayment;

public interface ClassPaymentRepository extends JpaRepository<ClassPayment, Long> {
	
	List<ClassPayment> findByMonthAndYear(String month, Integer year);
	
	List<ClassPayment> findByMonthAndYearAndStudentIdIn(String month, Integer year, List<Long> studentId);

}
