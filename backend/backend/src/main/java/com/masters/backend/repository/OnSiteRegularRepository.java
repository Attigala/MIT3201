package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.OnSiteRegular;

public interface OnSiteRegularRepository extends JpaRepository<OnSiteRegular, Long> {
	
	public List<OnSiteRegular> findByDay(String day);
	
	public List<OnSiteRegular> findByDayAndHallId(String day, Long hallId);
	
	public OnSiteRegular findFirstByClassContract(Long classContract);

}
