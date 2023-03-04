package com.masters.backend.dto;

import java.sql.Date;
import java.util.List;

public class ExemptionStatistics {
	
	private List<ExemptionSummaryDto> exemptionSummary;
	
	private String medium;
	private String subject;
	private String grade;
	private String teacher;
	private String student;
	private String guardian;
	private Date addedDate;
	private String addedBy;
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getGuardian() {
		return guardian;
	}
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public List<ExemptionSummaryDto> getExemptionSummary() {
		return exemptionSummary;
	}
	public void setExemptionSummary(List<ExemptionSummaryDto> exemptionSummary) {
		this.exemptionSummary = exemptionSummary;
	}

}
