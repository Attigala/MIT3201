package com.masters.backend.repository;

import java.sql.Date;

public class NewReciept {
	Long id;
	Long classContractId;
	Long guardianId;
	Long studentId;
	String month;
	Integer year;
	Date paymentDate;
	
	
	public NewReciept(Long id, Long classContractId, Long guardianId, Long studentId, String month, Integer year,
			Date paymentDate) {
		super();
		this.id = id;
		this.classContractId = classContractId;
		this.guardianId = guardianId;
		this.studentId = studentId;
		this.month = month;
		this.year = year;
		this.paymentDate = paymentDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClassContractId() {
		return classContractId;
	}
	public void setClassContractId(Long classId) {
		this.classContractId = classId;
	}
	public Long getGuardianId() {
		return guardianId;
	}
	public void setGuardianId(Long guardianId) {
		this.guardianId = guardianId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	

}
