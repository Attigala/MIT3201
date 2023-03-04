package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.RequestSubscription;

public interface RequestSubscriptionRepository extends JpaRepository<RequestSubscription, Long> {
	
	List<RequestSubscription> findBySubjectAndMediumAndGrade(String subject, String medium, Integer grade);

}
