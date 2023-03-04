package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.AssignmentSubmission;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long>{
	
	public List<AssignmentSubmission> findByAssignmentId(Long assignmentId);
	
	public AssignmentSubmission findFirstByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
	
	public List<AssignmentSubmission> findByAssignmentIdAndStatus(Long assignmentId, String status);
	

}
