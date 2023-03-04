package com.masters.backend.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StudentClass {
	
	private @GeneratedValue @Id Long id;
	private Long studentId;
	private Long classContractId;
	private Integer year;
	private String month;
	private Date addedDate;
	private Integer paymentFlag;
	private Integer suspended;
	private Date suspendedDate;
	
	public Integer getSuspended() {
		return suspended;
	}
	public void setSuspended(Integer suspended) {
		this.suspended = suspended;
	}
	public Date getSuspendedDate() {
		return suspendedDate;
	}
	public void setSuspendedDate(Date suspendedDate) {
		this.suspendedDate = suspendedDate;
	}
	public Integer getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(Integer paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
