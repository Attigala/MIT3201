package com.masters.backend.dto;

public class UnpaidDto {
	
	private String studentName;
	private String classDetails;
	private String studentContactNumber;
	private String teacher;
	private String guardianName;
	private Long studentId;
	private Long classContractId;
	private Long guardianId;
	private Integer suspended;
	
	public Integer getSuspended() {
		return suspended;
	}
	public void setSuspended(Integer suspended) {
		this.suspended = suspended;
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
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassDetails() {
		return classDetails;
	}
	public void setClassDetails(String classDetails) {
		this.classDetails = classDetails;
	}
	public String getStudentContactNumber() {
		return studentContactNumber;
	}
	public void setStudentContactNumber(String studentContactNumber) {
		this.studentContactNumber = studentContactNumber;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public Long getGuardianId() {
		return guardianId;
	}
	public void setGuardianId(Long guardianId) {
		this.guardianId = guardianId;
	}
	
	

}
