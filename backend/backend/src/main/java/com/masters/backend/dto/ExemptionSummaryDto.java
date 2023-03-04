package com.masters.backend.dto;

public class ExemptionSummaryDto {
	
	private Long classId;
	private String teacher;
	private String className;
	private Long numberOfStudents;
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Long getNumberOfStudents() {
		return numberOfStudents;
	}
	public void setNumberOfStudents(Long numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}
	
	

}
