package com.masters.backend.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.OnsiteExtra;

public interface OnsiteExtraRepository extends JpaRepository<OnsiteExtra, Long> {
	
	public List<OnsiteExtra> findByHallIdAndDateBetween(Long hallId, Date startDate, Date endDate);

}
