package com.foucsr.crmportal.mysql.database.model.timesheet;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator;


@Entity
@Table(name = "TIMESHEET")
public class TimeSheetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIMESHEET_SEQ")
	@GenericGenerator(name = "TIMESHEET_SEQ", strategy = "com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TS_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%07d") })
	@Column(name = "TIMESHEET_ID")
	private String id;

	@Column(name = "EMP_ID")
	private String empId;

	@Column(name = "DATE")
	private Date date;

	@Column(name = "STATUS")
	private String Status;

	@Column(name = "APPROVER")
	private String approver;

	@Column(name = "MANAGER_COMMENTS", nullable = true)
	private String managerComments;

	@Column(name = "User_COMMENTS", nullable = true)
	private String userComments;

	@Column(name = "NOTIFICTION_SENT_TO", nullable = true)
	private String notifiedTo;

	@Column(name = "HOURS")
	private double hours;

	@Column(name = "MINUTES")
	private double minutes;

	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdOn;
	
	@Column(name = "SUBMITTED_ON")
	private LocalDateTime submittedOn;
	
	@Column(name = "APPROVED_ON")
	private LocalDateTime approvedOn;

	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
	private LocalDateTime updatedOn;
	
	@Column(name = "CALENDAR")
	private String calendar;
	
	
	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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

	/**
	 * @return the hours
	 */
	public double getHours() {
		return hours;
	}

	/**
	 * @param hours
	 *            the hours to set
	 */
	public void setHours(double hours) {
		this.hours = hours;
	}

	/**
	 * @return the minutes
	 */
	public double getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes
	 *            the minutes to set
	 */
	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}

	/**
	 * @return the notifiedTo
	 */
	public String getNotifiedTo() {
		return notifiedTo;
	}

	/**
	 * @param notifiedTo
	 *            the notifiedTo to set
	 */
	public void setNotifiedTo(String notifiedTo) {
		this.notifiedTo = notifiedTo;
	}

	/**
	 * @return the createdOn
	 */
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the updatedOn
	 */
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn
	 *            the updatedOn to set
	 */
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

}
