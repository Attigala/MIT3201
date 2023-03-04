package com.masters.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.AnswerDto;
import com.masters.backend.dto.ForumAnswerDto;
import com.masters.backend.dto.ForumQuestionDto;
import com.masters.backend.dto.QuestionDto;
import com.masters.backend.model.ForumAnswer;
import com.masters.backend.model.ForumQuestion;
import com.masters.backend.model.Student;
import com.masters.backend.model.Teacher;
import com.masters.backend.repository.ForumAnswerRepository;
import com.masters.backend.repository.ForumQuestionRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.repository.TeacherRepository;

@Service
public class ForumService {
	
	@Autowired ForumQuestionRepository iForumQuestionRepository;
	@Autowired TeacherRepository iTeacherRepository;
	@Autowired StudentRepository iStudentRepository;
	@Autowired ForumAnswerRepository iForumAnswerRepository;
	
	public ForumQuestion submitQuestion(ForumQuestionDto forumQuestionDto) {
		ForumQuestion forumQuestion = new ForumQuestion();
		
		if ("student".equals(forumQuestionDto.getUserClass())){
			
			Student student = iStudentRepository.findFirstByEmail(forumQuestionDto.getUsername());
			if(student != null) {
				forumQuestion.setClassId(forumQuestionDto.getClassId());
				forumQuestion.setQuestion(forumQuestionDto.getQuestion());
				forumQuestion.setSubmittedUserClass(forumQuestionDto.getUserClass());
				forumQuestion.setClassId(forumQuestionDto.getClassId());
				forumQuestion.setSubmittedUserId(student.getId());
				iForumQuestionRepository.save(forumQuestion);
			}
			
		} else {
			Teacher teacher = iTeacherRepository.findFirstByEmail(forumQuestionDto.getUsername());
			if(teacher != null) {
				forumQuestion.setClassId(forumQuestionDto.getClassId());
				forumQuestion.setQuestion(forumQuestionDto.getQuestion());
				forumQuestion.setSubmittedUserClass(forumQuestionDto.getUserClass());
				forumQuestion.setClassId(forumQuestionDto.getClassId());
				forumQuestion.setSubmittedUserId(teacher.getId());
				iForumQuestionRepository.save(forumQuestion);
			}
		}
		return forumQuestion;
	}
	
	public List<QuestionDto> getQuestions(Long classId) {
		List<QuestionDto> questions = new ArrayList<QuestionDto>();
		
		List<ForumQuestion> forumQuestions = iForumQuestionRepository.findByClassId(classId);
		
		for(ForumQuestion forumQuestion : forumQuestions) {
			if("teacher".equals(forumQuestion.getSubmittedUserClass())) {
				Teacher teacher = iTeacherRepository.findById(forumQuestion.getSubmittedUserId()).orElse(null);
				if(teacher != null) {
					QuestionDto question = new QuestionDto();
					question.setAskedBy("Ms/Mr." + teacher.getFirstName() + " " + teacher.getLastName());
					question.setId(forumQuestion.getId());
					question.setQuestion(forumQuestion.getQuestion());
					questions.add(question);
				}
			} else {
				Student student = iStudentRepository.findById(forumQuestion.getSubmittedUserId()).orElse(null);
				if(student != null) {
					QuestionDto question = new QuestionDto();
					question.setAskedBy(student.getFirstName()+ " "+ student.getLastName());
					question.setId(forumQuestion.getId());
					question.setQuestion(forumQuestion.getQuestion());
					questions.add(question);
				}
			}
		}
		return questions;
	}
	
	public ForumAnswer submitAnswer(ForumAnswerDto forumAnswerDto) {

		ForumAnswer forumAnswer = new ForumAnswer();
		if ("student".equals(forumAnswerDto.getUserClass())) {

			Student student = iStudentRepository.findFirstByEmail(forumAnswerDto.getUsername());
			if (student != null) {
				forumAnswer.setClassId(forumAnswerDto.getClassId());
				forumAnswer.setQuestionId(forumAnswerDto.getQuestion());
				forumAnswer.setUserClass(forumAnswerDto.getUserClass());
				forumAnswer.setClassId(forumAnswerDto.getClassId());
				forumAnswer.setAnswer(forumAnswerDto.getAnswer());
				forumAnswer.setUserId(student.getId());
				iForumAnswerRepository.save(forumAnswer);
			}

		} else {
			Teacher teacher = iTeacherRepository.findFirstByEmail(forumAnswerDto.getUsername());
			if (teacher != null) {
				forumAnswer.setClassId(forumAnswerDto.getClassId());
				forumAnswer.setQuestionId(forumAnswerDto.getQuestion());
				forumAnswer.setUserClass(forumAnswerDto.getUserClass());
				forumAnswer.setClassId(forumAnswerDto.getClassId());
				forumAnswer.setAnswer(forumAnswerDto.getAnswer());
				forumAnswer.setUserId(teacher.getId());
				iForumAnswerRepository.save(forumAnswer);
			}
		}
		return forumAnswer;

	}
	
	public List<AnswerDto> getAnswersForQuestion(Long questionId) {
		List<AnswerDto> answers = new ArrayList<>();
		
		List<ForumAnswer> forumAnswers = iForumAnswerRepository.findByQuestionId(questionId);
		
		for(ForumAnswer forumAnswer : forumAnswers) {
			if("teacher".equals(forumAnswer.getUserClass())) {
				Teacher teacher = iTeacherRepository.findById(forumAnswer.getUserId()).orElse(null);
				if(teacher != null) {
					AnswerDto answer = new AnswerDto();
					answer.setRepliedBy("Ms/Mr." + teacher.getFirstName() + " " + teacher.getLastName());
					answer.setId(forumAnswer.getId());
					answer.setAnswer(forumAnswer.getAnswer());
					answers.add(answer);
				}
			} else {
				Student student = iStudentRepository.findById(forumAnswer.getUserId()).orElse(null);
				if(student != null) {
					AnswerDto answer = new AnswerDto();
					answer.setRepliedBy(student.getFirstName() + " " + student.getLastName());
					answer.setId(forumAnswer.getId());
					answer.setAnswer(forumAnswer.getAnswer());
					answers.add(answer);
				}
			}
		}
		return answers;
	}

}
