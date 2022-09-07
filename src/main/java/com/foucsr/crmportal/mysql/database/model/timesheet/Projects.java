package com.foucsr.crmportal.mysql.database.model.timesheet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROJECTS")
public class Projects {
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="PROJECT_NAME")	
	private String projectName;
	
	@Column(name="PROJECT_CALENDER")	
	private String calender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCalender() {
		return calender;
	}

	public void setCalender(String calender) {
		this.calender = calender;
	}
	
	
	
	


}
