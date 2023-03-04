package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.AssignmentMark;

public interface AssignmentMarkRepository extends JpaRepository<AssignmentMark, Long>{
	
	public List<AssignmentMark> findByAssignmentId(Long assignmentId);
	public List<AssignmentMark> findByStudentId(Long studentId);
	public List<AssignmentMark> findByClassContractId(Long classContract);
	public List<AssignmentMark> findByStudentIdAndClassContractId(Long studentId, Long classContractId);

}
