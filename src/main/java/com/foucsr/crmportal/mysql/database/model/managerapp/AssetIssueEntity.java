package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.foucsr.crmportal.mysql.database.model.User;

@Entity
@Table(name = "ASSET_ISSUE")
public class AssetIssueEntity {

	@Id
	@SequenceGenerator(name = "ASSET_ISSUE_SEQ", sequenceName = "ASSET_ISSUE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSET_ISSUE_SEQ")
	@Column(name = "ASSET_ISSUE_ID")
	private Long asset_issue_id;

	@Column(name = "ASSET_ID")
	private Long asset_id;
	
	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "STATUS")
	private String Status;

	@Column(name = "SUBMITTED_ON")
	private LocalDateTime submittedOn;
	
	@Column(name = "APPROVED_ON")
	private LocalDateTime approvedOn;
	
	@Column(name = "APPROVER")
	private String approver;

	@Column(name = "REASON")
	private String reason;
	

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

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}
	

	public AssetIssueEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
