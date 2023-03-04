package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.ClassRequest;

public interface ClassRequestRepository extends JpaRepository<ClassRequest, Long>{
	
	List<ClassRequest> findByClassId(Long classId);
	
	List<ClassRequest> findByClassIdIn(List<Long> classIds);
	
	List<ClassRequest> findByStatus(String status);
	
	List<ClassRequest> findByStatusAndMode(String status, String mode);

}
