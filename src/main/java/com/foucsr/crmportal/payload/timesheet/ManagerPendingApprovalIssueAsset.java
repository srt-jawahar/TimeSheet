package com.foucsr.crmportal.payload.timesheet;

import java.time.LocalDateTime;

public class ManagerPendingApprovalIssueAsset {
	
	
	private Long asset_issue_id;
	
	private Long asset_id;

	private String empId;
	
	private String reason;

	private String Status;

	private String approver;

	private LocalDateTime submittedOn;
	
	private LocalDateTime approvedOn;
	

	public Long getAsset_issue_id() {
		return asset_issue_id;
	}

	public void setAsset_issue_id(Long asset_issue_id) {
		this.asset_issue_id = asset_issue_id;
	}

	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	

}
