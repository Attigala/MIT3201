package com.masters.backend.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.OnlineExtra;
import com.masters.backend.model.OnsiteExtra;

public interface OnlineExtraRepository extends JpaRepository<OnlineExtra, Long>{
	public List<OnlineExtra> findByDateBetween( Date startDate, Date endDate);

}
