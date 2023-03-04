package com.masters.backend.dto;

import java.util.List;
import java.util.Map;

public class ClassStatisticsDto {
	
	private Long classId;
	private String teacherName;
	private Long totalStudents;
    private Map<String, Long> totalPerMonth;
	private String subject;
	private String medium;
	private String grade;
	
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Long getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(Long totalStudents) {
		this.totalStudents = totalStudents;
	}
	public Map<String, Long> getTotalPerMonth() {
		return totalPerMonth;
	}
	public void setTotalPerMonth(Map<String, Long> totalPerMonth) {
		this.totalPerMonth = totalPerMonth;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	

}
