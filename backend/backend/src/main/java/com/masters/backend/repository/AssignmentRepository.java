package com.masters.backend.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	
	List<Assignment> findByClassId(Long classId);
	
	List<Assignment> findByClassIdAndDueDateAfter(Long ClassId, Date afterDate);
	
	List<Assignment> findByClassIdIn(List<Long> classId);

}
