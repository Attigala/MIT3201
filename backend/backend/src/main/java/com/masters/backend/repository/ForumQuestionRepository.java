package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.ForumQuestion;

public interface ForumQuestionRepository extends JpaRepository<ForumQuestion,Long> {
	List<ForumQuestion> findByClassId(Long classId);
}
