package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.ExtraClassNotification;

public interface ExtraClassNotificationRepository extends JpaRepository<ExtraClassNotification, Long> {
	
	public List<ExtraClassNotification> findByStatus(String status);

}
