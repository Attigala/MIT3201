package com.masters.backend.dto;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class PaymentReceiptDto {
	
	private Long childId;
	private Long classId;
	private MultipartFile file;
	private String month;
	public Long getChildId() {
		return childId;
	}
	public void setChildId(Long childId) {
		this.childId = childId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
