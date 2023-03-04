package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.backend.dto.AnswerDto;
import com.masters.backend.dto.ForumAnswerDto;
import com.masters.backend.dto.ForumQuestionDto;
import com.masters.backend.dto.QuestionDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ForumAnswer;
import com.masters.backend.model.ForumQuestion;
import com.masters.backend.service.ForumService;

@RestController
public class ForumController {
	
	@Autowired ForumService iForumService;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/forum/question")
	public ForumQuestion addQuestion(@RequestBody ForumQuestionDto forumQuestionDto) {
		return iForumService.submitQuestion(forumQuestionDto);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/forum/question")
	public List<QuestionDto> getQuestionsForClass(@RequestParam ("classId") Long classId) {
		return iForumService.getQuestions(classId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/forum/answer")
	public ForumAnswer addAnswer(@RequestBody ForumAnswerDto forumAnswerDto) {
		return iForumService.submitAnswer(forumAnswerDto);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/forum/answer")
	public List<AnswerDto> getAnswersForQuestion(@RequestParam ("questionId") Long questionId) {
		return iForumService.getAnswersForQuestion(questionId);
	}
}
