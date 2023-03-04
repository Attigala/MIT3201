package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.RequestedClasses;

public interface RequestedClassRepository extends JpaRepository<RequestedClasses, Long> {
	
	List<RequestedClasses> findByYear(Integer year);

}
