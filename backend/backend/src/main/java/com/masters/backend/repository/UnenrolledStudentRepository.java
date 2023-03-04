package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.UnenrolledStudent;

public interface UnenrolledStudentRepository extends JpaRepository<UnenrolledStudent, Long> {
	
	List<UnenrolledStudent> findByClassContractId(Long classContractId);

}
