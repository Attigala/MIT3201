package com.masters.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	Teacher findFirstByEmail(String email);
}
