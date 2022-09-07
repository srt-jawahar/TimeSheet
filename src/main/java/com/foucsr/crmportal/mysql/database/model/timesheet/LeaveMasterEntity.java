package com.foucsr.crmportal.mysql.database.model.timesheet;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="LEAVE_MASTER")
public class LeaveMasterEntity {
	

	@Id
	@SequenceGenerator(name = "LEAVE_MASTER_SEQ", sequenceName = "LEAVE_MASTER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEAVE_MASTER_SEQ")
	@Column(name="LEAVE_MASTER_ID")
	private Long leave_master_id;
	 
	@Column(name="CATEGORY_NAME")
	private String category_name;
	
	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="COUNTRY_NAME")
	private String country_name;
	
	@Column(name="COUNTRY_CODE")
	private String country_code;
	
	@Column(name="TOTAL_LEAVE")
	private Double total_leave;
	
	@Column(name="IS_DOC_REQUIRED")
	private String is_doc_required;
	
	@Column(name="IS_REMARKS_REQUIRED")
	private String is_remarks_required;
	
	@Column(name="CAN_APPLY_CONTINOUSLY")
	private String can_apply_continously;
	
	@Column(name="IS_ACTIVE")
	private String is_active;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "leaveMaster")
	private List<LeaveBalanceEntity> leaveBalances;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "leaveMaster")
	private List<LeaveAppliedEntity> leaveApplies;

	public Long getLeave_master_id() {
		return leave_master_id;
	}

	public void setLeave_master_id(Long leave_master_id) {
		this.leave_master_id = leave_master_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public Double getTotal_leave() {
		return total_leave;
	}

	public void setTotal_leave(Double total_leave) {
		this.total_leave = total_leave;
	}

	public String getIs_doc_required() {
		return is_doc_required;
	}

	public void setIs_doc_required(String is_doc_required) {
		this.is_doc_required = is_doc_required;
	}

	public String getIs_remarks_required() {
		return is_remarks_required;
	}

	public void setIs_remarks_required(String is_remarks_required) {
		this.is_remarks_required = is_remarks_required;
	}

	public String getCan_apply_continously() {
		return can_apply_continously;
	}

	public void setCan_apply_continously(String can_apply_continously) {
		this.can_apply_continously = can_apply_continously;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	@JsonIgnore
	public List<LeaveBalanceEntity> getLeaveBalances() {
		return leaveBalances;
	}

	public void setLeaveBalances(List<LeaveBalanceEntity> leaveBalances) {
		this.leaveBalances = leaveBalances;
	}

	@JsonIgnore
	public List<LeaveAppliedEntity> getLeaveApplies() {
		return leaveApplies;
	}

	public void setLeaveApplies(List<LeaveAppliedEntity> leaveApplies) {
		this.leaveApplies = leaveApplies;
	}
	
	
	
}
