package com.masters.backend.dto;

public class StudentClassSuspensionDto {
	
	private Long studentId;
	private Long classContractId;
	private Long studentClassId;
	private String studentName;
	private String guardianName;
	private String contactNumber;
	private String classDetails;	
	private Integer guardianSuspended;
	
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
	public Long getStudentClassId() {
		return studentClassId;
	}
	public void setStudentClassId(Long studentClassId) {
		this.studentClassId = studentClassId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Integer getGuardianSuspended() {
		return guardianSuspended;
	}
	public void setGuardianSuspended(Integer guardianSuspended) {
		this.guardianSuspended = guardianSuspended;
	}
	public void setClassDetails(String classDetails) {
		// TODO Auto-generated method stub
		this.classDetails = classDetails;
	}
	
	public String getClassDetails( ) {
		return classDetails;
	}
	
	

}
