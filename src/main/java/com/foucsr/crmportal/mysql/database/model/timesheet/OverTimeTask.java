package com.foucsr.crmportal.mysql.database.model.timesheet;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator;


@Entity
@Table(name = "OVERTIME_TASK")
public class OverTimeTask {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OVERTIME_TASK_SEQ")
	@GenericGenerator(name = "OVERTIME_TASK_SEQ", strategy = "com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "OT_TASK_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "OT_TASK_ID", nullable = false)
	private String ot_task_id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OVER_TIME_ID")
	@Fetch(FetchMode.JOIN)
	private OverTime overtimeId;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "PROJECT")
	private String project;

	@Column(name = "ACTIVITY")
	private String activity;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "PHASE",nullable = true)
	private String phase;

	@Column(name = "MINUTES")
	private double minutes;

	@Column(name = "REMARKS", nullable = true)
	private String remarks;
	
	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdOn;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
	private LocalDateTime updatedOn;


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * @param phase
	 *            the phase to set
	 */
	public void setPhase(String phase) {
		this.phase = phase;
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
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOt_task_id() {
		return ot_task_id;
	}

	public void setOt_task_id(String ot_task_id) {
		this.ot_task_id = ot_task_id;
	}

	public OverTime getOvertimeId() {
		return overtimeId;
	}

	public void setOvertimeId(OverTime overtimeId) {
		this.overtimeId = overtimeId;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
}
