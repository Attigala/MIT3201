package com.masters.backend.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	private @Id @GeneratedValue Long id;
	private String username;
	private String password;
	private String role;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
