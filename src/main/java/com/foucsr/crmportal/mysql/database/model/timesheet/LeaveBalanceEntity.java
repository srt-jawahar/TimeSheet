package com.foucsr.crmportal.mysql.database.model.timesheet;

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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foucsr.crmportal.mysql.database.model.User;

@Entity
@Table(name="LEAVE_BALANCE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "USER_ID",
                "LEAVE_MASTER_ID",
                "YEAR"
            })
    })
public class LeaveBalanceEntity {
	

	@Id
	@SequenceGenerator(name = "LEAVE_BALANCE_SEQ", sequenceName = "LEAVE_BALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEAVE_BALANCE_SEQ")
	@Column(name="LEAVE_BALANCE_ID")
	private Long leave_balance_id;
	
	@Column(name="USER_ID")
	private Long user_id;
	 
	@Column(name="CATEGORY_NAME")
	private String category_name;
	
	@Column(name="COUNTRY_CODE")
	private String country_code;
	
	@Column(name="TOTAL_LEAVE")
	private Double total_leave;
	
	@Column(name="BALANCE_LEAVE")
	private Double balance_leave;
	
	@Column(name="YEAR")
	private String year;
	
	@Column(name="LEAVE_MASTER_ID")
	private Long leave_master_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEAVE_MASTER_ID", insertable = false, updatable = false)
	private LeaveMasterEntity leaveMaster;


	public Long getLeave_balance_id() {
		return leave_balance_id;
	}

	public void setLeave_balance_id(Long leave_balance_id) {
		this.leave_balance_id = leave_balance_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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

	public Double getBalance_leave() {
		return balance_leave;
	}

	public void setBalance_leave(Double balance_leave) {
		this.balance_leave = balance_leave;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public Double getTotal_leave() {
		return total_leave;
	}

	public void setTotal_leave(Double total_leave) {
		this.total_leave = total_leave;
	}
	
	
	
	
}
