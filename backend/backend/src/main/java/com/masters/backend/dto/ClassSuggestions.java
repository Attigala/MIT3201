package com.masters.backend.dto;

import java.sql.Date;

public class ClassSuggestions {
	
	private Long requestId;
	private Long classId;
	private Date date;
	private String timeslot;
	private String hallName;
	private Long hallNumber;
	private String mode;
	private String Status;
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTimeslot() {
		return timeslot;
	}
	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public Long getHallNumber() {
		return hallNumber;
	}
	public void setHallNumber(Long hallNumber) {
		this.hallNumber = hallNumber;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

}
