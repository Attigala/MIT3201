package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.StudentClass;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
	List<StudentClass> findByStudentId(Long studentId);

	
	List<StudentClass> findByStudentIdIn(List<Long> studentId);
	List<StudentClass> findByStudentIdNotIn(List<Long> studentId);
	
	List<StudentClass> findByStudentIdNotInAndClassContractIdInAndSuspended(List<Long> studentId, List<Long> classContractId, Integer suspended);
	
	List<StudentClass> findByStudentIdInAndClassContractIdNotInAndSuspended(List<Long> studentId, List<Long> classContractId, Integer suspended);

	List<StudentClass> findByClassContractId(Long classContractId);
	
	StudentClass findFirstByStudentIdAndClassContractId(Long studentId, Long ClassContractId);
	
	List<StudentClass> findByIdNotIn(List<Long> id);
	
	List<StudentClass> findBySuspended(Integer suspended);
}
