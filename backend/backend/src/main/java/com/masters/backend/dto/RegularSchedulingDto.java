package com.masters.backend.dto;

public class RegularSchedulingDto {
	private Long id;
	private Long teacherId;
	private Long classContractId;
	private String date;
	private String timeSlot;
	private String mode;
	private Long hallId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Long getClassContractId() {
		return classContractId;
	}
	public void setClassContractId(Long classContractId) {
		this.classContractId = classContractId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Long getHallId() {
		return hallId;
	}
	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}
	
	

}
