package com.foucsr.crmportal.payload.timesheet;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ManagerPendingApprovalLeave {
	
	private String leave_applied_id;

	private String empId;

	private String Status;

	private String approver;

	private String managerComments;

	private String userComments;

	private LocalDateTime submittedOn;
	
	private LocalDateTime approvedOn;

	private String name;
	
	private String from_date;
	
	private String to_date;

	public String getLeave_applied_id() {
		return leave_applied_id;
	}

	public void setLeave_applied_id(String leave_applied_id) {
		this.leave_applied_id = leave_applied_id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getManagerComments() {
		return managerComments;
	}

	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	public String getUserComments() {
		return userComments;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	public LocalDateTime getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(LocalDateTime submittedOn) {
		this.submittedOn = submittedOn;
	}

	public LocalDateTime getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(LocalDateTime approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	

	}
