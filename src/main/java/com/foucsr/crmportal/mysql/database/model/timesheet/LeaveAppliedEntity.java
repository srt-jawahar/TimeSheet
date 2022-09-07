package com.foucsr.crmportal.mysql.database.model.timesheet;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator;


@Entity
@Table(name = "LEAVE_APPLIED_TBL")
public class LeaveAppliedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEAVE_APPLIED_TBL_SEQ")
	@GenericGenerator(name = "LEAVE_APPLIED_TBL_SEQ", strategy = "com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "L_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%07d") })
	@Column(name = "LEAVE_APPLIED_ID")
	private String leave_applied_id;

	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "USER_ID")
	private Long user_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@Column(name = "FROM_DATE")
	private String from_date;
	
	@Column(name = "TO_DATE")
	private String to_date;
	
	@Column(name = "FROM_FIRST_HALF")
	private String from_first_half;
	
	@Column(name = "FROM_SECOND_HALF")
	private String from_second_half;
	
	@Column(name = "TO_FIRST_HALF")
	private String to_first_half;
	
	@Column(name = "TO_SECOND_HALF")
	private String to_second_half;
	
	@Column(name = "MOBILE_NUM")
	private String mobile_num;

	@Column(name = "STATUS")
	private String Status;

	@Column(name = "APPROVER")
	private String approver;

	@Column(name = "MANAGER_COMMENTS", nullable = true)
	private String managerComments;

	@Column(name = "User_COMMENTS", nullable = true)
	private String userComments;

	@Column(name = "SUBMITTED_ON")
	private LocalDateTime submittedOn;
	
	@Column(name = "APPROVED_ON")
	private LocalDateTime approvedOn;
	
	@Column(name="APPLIED_DAYS")
	private Double applied_days;
	
	@Column(name="LEAVE_MASTER_ID")
	private Long leave_master_id;
	
	@Column(name="CATEGORY_NAME")
	private String category_name;
	
	@Column(name="COUNTRY_CODE")
	private String country_code;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEAVE_MASTER_ID", insertable = false, updatable = false)
	private LeaveMasterEntity leaveMaster;

	
	/**
	 * @return the submittedOn
	 */
	public LocalDateTime getSubmittedOn() {
		return submittedOn;
	}

	/**
	 * @param submittedOn the submittedOn to set
	 */
	public void setSubmittedOn(LocalDateTime submittedOn) {
		this.submittedOn = submittedOn;
	}

	/**
	 * @return the approvedOn
	 */
	public LocalDateTime getApprovedOn() {
		return approvedOn;
	}

	/**
	 * @param approvedOn the approvedOn to set
	 */
	public void setApprovedOn(LocalDateTime approvedOn) {
		this.approvedOn = approvedOn;
	}


	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}

	/**
	 * @return the approver
	 */
	public String getApprover() {
		return approver;
	}

	/**
	 * @param approver
	 *            the approver to set
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

	/**
	 * @return the managerComments
	 */
	public String getManagerComments() {
		return managerComments;
	}

	/**
	 * @param managerComments
	 *            the managerComments to set
	 */
	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	/**
	 * @return the userComments
	 */
	public String getUserComments() {
		return userComments;
	}

	/**
	 * @param userComments
	 *            the userComments to set
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	public String getLeave_applied_id() {
		return leave_applied_id;
	}

	public void setLeave_applied_id(String leave_applied_id) {
		this.leave_applied_id = leave_applied_id;
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

	public String getMobile_num() {
		return mobile_num;
	}

	public void setMobile_num(String mobile_num) {
		this.mobile_num = mobile_num;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFrom_first_half() {
		return from_first_half;
	}

	public void setFrom_first_half(String from_first_half) {
		this.from_first_half = from_first_half;
	}

	public String getFrom_second_half() {
		return from_second_half;
	}

	public void setFrom_second_half(String from_second_half) {
		this.from_second_half = from_second_half;
	}

	public String getTo_first_half() {
		return to_first_half;
	}

	public void setTo_first_half(String to_first_half) {
		this.to_first_half = to_first_half;
	}

	public String getTo_second_half() {
		return to_second_half;
	}

	public void setTo_second_half(String to_second_half) {
		this.to_second_half = to_second_half;
	}

	public Double getApplied_days() {
		return applied_days;
	}

	public void setApplied_days(Double applied_days) {
		this.applied_days = applied_days;
	}

	public Long getLeave_master_id() {
		return leave_master_id;
	}

	public void setLeave_master_id(Long leave_master_id) {
		this.leave_master_id = leave_master_id;
	}

	@JsonIgnore
	public LeaveMasterEntity getLeaveMaster() {
		return leaveMaster;
	}

	public void setLeaveMaster(LeaveMasterEntity leaveMaster) {
		this.leaveMaster = leaveMaster;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}


}
