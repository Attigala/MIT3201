package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masters.backend.model.Guardian;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudentClass;

public interface StudentRepository extends JpaRepository<Student, Long>{
	List<Student> findByGuardian(Guardian guardian);
	
	List<Student> findByIdNotIn(List<Long> studentId);
	
	Student findFirstByEmail(String email);
}
