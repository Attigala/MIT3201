package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long>{
	
	public List<Hall> findByCapacityGreaterThan(Integer capacity);

}
