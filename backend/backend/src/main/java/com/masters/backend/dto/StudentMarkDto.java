package com.masters.backend.dto;

public class StudentMarkDto {
	
	private Long studentId;
	private Long classContractId;
	private Long assignmentId;
	private Integer mark;
	private String studentName;
	private Long assignmentMarkId;
	private String classContractName;
	
	public Long getAssignmentMarkId() {
		return assignmentMarkId;
	}
	public void setAssignmentMarkId(Long assignmentMarkId) {
		this.assignmentMarkId = assignmentMarkId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getClassContractId() {
		return classContractId;
	}
	public void setClassContractId(Long classContractId) {
		this.classContractId = classContractId;
	}
	public Long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassContractName() {
		return classContractName;
	}
	public void setClassContractName(String classContractName) {
		this.classContractName = classContractName;
	}
	
	

}
