package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.OnlineRegular;

public interface OnlineRegularRepository extends JpaRepository<OnlineRegular, Long>{
	OnlineRegular findFirstByClassContract(Long ClassContract);
	OnlineRegular findFirstByDayAndTimeSlot(String day, String timeSlot);
	List<OnlineRegular> findByDay(String day);
}
