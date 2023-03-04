package com.masters.backend.dto;

public class TeacherOverviewDto {
	
	private Long classId;
	private String className;
	private TeacherHomeScheduleDto schedule;
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public TeacherHomeScheduleDto getSchedule() {
		return schedule;
	}
	public void setSchedule(TeacherHomeScheduleDto schedule) {
		this.schedule = schedule;
	}
}
