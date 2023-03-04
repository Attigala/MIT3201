package com.masters.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.backend.model.ForumAnswer;

public interface ForumAnswerRepository extends JpaRepository<ForumAnswer, Long> {
	
	List<ForumAnswer> findByQuestionId(Long questionId);

}
