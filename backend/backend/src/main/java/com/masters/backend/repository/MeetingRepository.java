package com.masters.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
