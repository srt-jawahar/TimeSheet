package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.util.Date;

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

import com.foucsr.crmportal.model.audit.DateAudit;
import com.foucsr.crmportal.mysql.database.model.User;

@Entity
@Table(name = "ASSET_DETAILS")
public class AssetModelEntity extends DateAudit
{
	@Id
	@SequenceGenerator(name = "ASSET_DETAILS_SEQ", sequenceName = "ASSET_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSET_DETAILS_SEQ")
	@Column(name="ASSET_ID", unique = true, nullable = false)
	private Long asset_id;

	@Column(name="USER_ID")
	private Long user_id;

	@Column(name="ASSET_CATEGORY")
	private String asset_category;
	
	@Column(name="MAKE")
	private String make;
	
	@Column(name ="MODEL")
	private String model;
	
	@Column(name = "ASSET_SERIAL_NO")
	private String asset_serial_no;
	
	@Column(name = "BATTERY_SERIAL_NO")
	private String battery_serial_no;
	
	@Column (name = "PRODUCT_ID")
	private String product_id;
	
	@Column( name= "OS_VERSION")
	private String os_version;
	
	@Column(name="OS_KEY")
	private String os_key;

	@Column(name="OS_TYPE")
	private String os_type;

	@Column(name="RAM")
	private String ram;
	
	@Column(name="DISPLAY_SIZE")
	private String display_size;

	@Column(name="STORAGE")
	private String storage;

	@Column(name="PROBLEM")
	private String problem;

	@Column(name="CONDITIONS")
	private String conditions;

	@Column(name="VALUE_OF_ASSET")
	private String value_of_asset;

	@Column(name="START_DATE")
	private Date start_date;

	@Column(name="END_DATE")
	private Date end_date;

	@Column(name="REMARKS")
	private String remarks;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	
	
	
	
	

	public AssetModelEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getUser_id() {
		return user_id;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}



	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getAsset_category() {
		return asset_category;
	}

	public void setAsset_category(String asset_category) {
		this.asset_category = asset_category;
	}

	public String getAsset_serial_no() {
		return asset_serial_no;
	}

	public void setAsset_serial_no(String asset_serial_no) {
		this.asset_serial_no = asset_serial_no;
	}

	public String getBattery_serial_no() {
		return battery_serial_no;
	}

	public void setBattery_serial_no(String battery_serial_no) {
		this.battery_serial_no = battery_serial_no;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
		
	}

	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getOs_key() {
		return os_key;
	}

	public void setOs_key(String os_key) {
		this.os_key = os_key;
	}

	public String getOs_type() {
		return os_type;
	}

	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getDisplay_size() {
		return display_size;
	}

	public void setDisplay_size(String display_size) {
		this.display_size = display_size;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getValue_of_asset() {
		return value_of_asset;
	}

	public void setValue_of_asset(String value_of_asset) {
		this.value_of_asset = value_of_asset;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
