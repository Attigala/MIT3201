package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.Guardian;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
	Guardian findFirstByEmail(String email);
	
	List<Guardian> findBySuspended(Integer suspended);
}
