package com.masters.backend.dto;

public class ForumAnswerDto {
	private Long question;
	private String answer;
	private String userClass;
	private String username;
	private Long classId;
	public Long getQuestion() {
		return question;
	}
	public void setQuestion(Long question) {
		this.question = question;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
