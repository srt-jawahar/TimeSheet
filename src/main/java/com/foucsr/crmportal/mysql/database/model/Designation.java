package com.foucsr.crmportal.mysql.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_DESIGNATION")
public class Designation {
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="DESIGNATION")	
	private String designation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	

}
