package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.foucsr.crmportal.model.audit.DateAudit;

@Entity
@Table(name = "ASSET_USER_DETAILS")
public class AssetUserEntity extends DateAudit {
	
	@Id
	@SequenceGenerator(name = "ASSET_USER_DETAILS_SEQ", sequenceName = "ASSET_USER_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSET_USER_DETAILS_SEQ")
	@Column(name="ASSET_USER_ID")
	private Long asset_user_id;
	
	
	@Column(name="ASSET_ID")
	private Long asset_id;
	

	@Column(name="USER_ID")
	private Long user_id;
	
	@Column(name="START_DATE")
	private Date start_date;

	@Column(name="END_DATE")
	private Date end_date;

	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	

	public Long getAsset_user_id() {
		return asset_user_id;
	}

	public void setAsset_user_id(Long asset_user_id) {
		this.asset_user_id = asset_user_id;
	}

	public AssetUserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
