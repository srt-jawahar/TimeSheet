package com.foucsr.crmportal.payload;


import java.time.LocalDate;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.foucsr.crmportal.util.StringPrefixedSequenceIdGenerator;


public class TimeSheetUpload {

	
	private String id;
 

	private String category;


	private String project;

	
	private String activity;
	
	private String calendar;
	
	
	private String status;

	private String phase;

	
	private double minutes;

	
	private String remarks;
	
	private LocalDate timesheetDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public double getMinutes() {
		return minutes;
	}

	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	
	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public LocalDate getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(LocalDate timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	@Override
	public String toString() {
		return "TimeSheetUpload [id=" + id + ", category=" + category + ", project=" + project + ", activity="
				+ activity + ", calendar=" + calendar + ", status=" + status + ", phase=" + phase + ", minutes="
				+ minutes + ", remarks=" + remarks + ", timesheetDate=" + timesheetDate + "]";
	}

	
	
}
