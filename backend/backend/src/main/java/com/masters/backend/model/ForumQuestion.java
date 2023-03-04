package com.masters.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ForumQuestion {
	@GeneratedValue @Id Long id;
	
	private Long classId;
	
	private String question;
	
	private String submittedUserClass;
	
	private Long submittedUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSubmittedUserClass() {
		return submittedUserClass;
	}

	public void setSubmittedUserClass(String submittedUserClass) {
		this.submittedUserClass = submittedUserClass;
	}

	public Long getSubmittedUserId() {
		return submittedUserId;
	}

	public void setSubmittedUserId(Long submittedUserId) {
		this.submittedUserId = submittedUserId;
	}
	
	
}
