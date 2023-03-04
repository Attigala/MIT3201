package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.NotifyClassSubscriber;

public interface NotifyClassSubscriberRepository extends JpaRepository<NotifyClassSubscriber, Long>{
	
	List<NotifyClassSubscriber> findByStatus(String status);

}
