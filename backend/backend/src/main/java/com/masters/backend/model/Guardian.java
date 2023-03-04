package com.masters.backend.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Devni Attigala
 *
 */
@Entity
public class Guardian {
	@Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String nic;
	private String relationship;
	private String contactNumber;
	private String email;
	private String houseNumber;
	private String firstStreet;
	private String secondStreet;
	private String city;
	private Integer suspended;
	private Date suspendedDate;
	
	@OneToMany(mappedBy = "guardian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Set<Student> student;
	
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getFirstStreet() {
		return firstStreet;
	}
	public void setFirstStreet(String firstStreet) {
		this.firstStreet = firstStreet;
	}
	public String getSecondStreet() {
		return secondStreet;
	}
	public void setSecondStreet(String secondStreet) {
		this.secondStreet = secondStreet;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
