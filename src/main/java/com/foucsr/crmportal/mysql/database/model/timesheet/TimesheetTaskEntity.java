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
@Table(name = "TIMESHEET_TASK")
public class TimesheetTaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIMESHEET_TASK_SEQ")
	@GenericGenerator(name = "TIMESHEET_TASK_SEQ", strategy = "com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TS_TASK_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "TASK_ID", nullable = false)
	private String id;

	//@Column(name = "TIEMSHEET_ID")
	//private String timesheetId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIMESHEET_ID")
	@Fetch(FetchMode.JOIN)
	private TimeSheetEntity timesheetId;

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

	@Override
	public String toString() {
		return "TimesheetTaskEntity [id=" + id + ", timesheetId=" + timesheetId + ", category=" + category
				+ ", project=" + project + ", activity=" + activity + ", status=" + status + ", phase=" + phase
				+ ", minutes=" + minutes + ", remarks=" + remarks + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}

	@Column(name = "MINUTES")
	private double minutes;

	@Column(name = "REMARKS", nullable = true)
	private String remarks;
	
	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdOn;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
	private LocalDateTime updatedOn;


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
	 * @return the timesheetId
	 */
	public TimeSheetEntity getTimesheetId() {
		return timesheetId;
	}

	/**
	 * @param timesheetId
	 *            the timesheetId to set
	 */
	public void setTimesheetId(TimeSheetEntity timesheetId) {
		this.timesheetId = timesheetId;
	}

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
}
