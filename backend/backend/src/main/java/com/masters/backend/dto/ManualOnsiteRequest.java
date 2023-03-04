package com.masters.backend.dto;

import java.sql.Date;

public class ManualOnsiteRequest {
	
	private Long classContract;
	private Date date;
	private String timeSlot;
	private Long requestId;
	private Long hallId;
	public Long getClassContract() {
		return classContract;
	}
	public void setClassContract(Long classContract) {
		this.classContract = classContract;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Long getHallId() {
		return hallId;
	}
	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}

}
