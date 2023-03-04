package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.SuspensionNotification;

public interface SuspensionNotificationRepository extends JpaRepository<SuspensionNotification, Long> {
	
	public List<SuspensionNotification> findByStatus(String status);

}
