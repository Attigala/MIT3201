package com.masters.backend.dto;

import java.sql.Date;

public class StudyMaterialDto {
	
	private Long classId;
	private String name;
	private String type;
	private String url;
	private long size;
	private Date dueDate;
	private long id;
	private String markSheetUrl;
	
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMarkSheetUrl() {
		return markSheetUrl;
	}
	public void setMarkSheetUrl(String markSheetUrl) {
		this.markSheetUrl = markSheetUrl;
	}
	
	

}
