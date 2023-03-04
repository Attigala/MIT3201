package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.masters.backend.model.ClassContract;
import com.masters.backend.model.Teacher;

public interface ClassContractRepository extends JpaRepository<ClassContract, Long>, JpaSpecificationExecutor{
	List<ClassContract> findByIdIn(List<Long> ids);
	List<ClassContract> findByTeacher(Teacher teacher);
}
