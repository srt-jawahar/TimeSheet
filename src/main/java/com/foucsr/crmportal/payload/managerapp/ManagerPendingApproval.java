package com.foucsr.crmportal.payload.managerapp;

import javax.persistence.Column;

import com.foucsr.crmportal.mysql.database.model.managerapp.UsersKpiAndKra;

public class ManagerPendingApproval {
	
	
	private UsersKpiAndKra usersKpiAndKra;
	
	private String username;
    private String name;
    private String employeeId;
    private String designation;
	private String status;    
	private String date;
	private String submittedDate;
    
    
    
	public UsersKpiAndKra getUsersKpiAndKra() {
		return usersKpiAndKra;
	}
	public void setUsersKpiAndKra(UsersKpiAndKra usersKpiAndKra) {
		this.usersKpiAndKra = usersKpiAndKra;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	
    
    

}
