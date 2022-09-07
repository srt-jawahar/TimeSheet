package com.foucsr.crmportal.mysql.database.model.managerapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LAPTOP_DETAILS")
public class LaptopEntity {

	@Id
	@SequenceGenerator(name = "LAPTOP_DETAILS_SEQ", sequenceName = "LAPTOP_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LAPTOP_DETAILS_SEQ")

	@Column(name = "LAPTOP_DETAILS_ID")
	private Long laptop_details_id;

	@Column(name = "EMPLOYEE_NAME")
	private String employee_name;

	@Column(name = "EMP_CODE")
	private String emp_code;

	@Column(name = "DEPARTMENT")
	private String department;

	@Column(name = "FOCUSR_LAPTOP")
	private String focusr_laptop;

	@Column(name = "MAKE")
	private String make;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "ASSET_TYPE")
	private String asset_type;

	@Column(name = "PREVIOUSLY_USED_BY")
	private String previously_used_by;

	@Column(name = "LAPTOP_SERIAL_NO")
	private String laptop_serial_no;

	@Column(name = "BATTERY_SERIAL_NO")
	private String battery_serial_no;

	@Column(name = "OS_VERSION")
	private String os_version;

	@Column(name = "WINDOWS_PRODUCT_ID")
	private String windows_product_id;

	@Column(name = "RAM")
	private String ram;

	@Column(name = "STORAGE")
	private String storage;

	@Column(name = "ANTI_VIRUS_ENABLED")
	private String anti_virus_enabled;

	@Column(name = "ANTI_VIRUS_TYPE")
	private String anti_virus_type;

	@Column(name = "EMAIL_CONFIGURATION")
	private String email_configuration;

	@Column(name = "OUTLOOK_VERSION")
	private String outlook_version;

	@Column(name = "DATE_OF_ASSET_RECEIPT")
	private String date_of_asset_receipt;

	@Column(name = "CHARGER_WORKING ")
	private String charger_working;

	@Column(name = "OTHER_SOFTWARE_INSTALLED ")
	private String other_software_installed;

	public Long getLaptop_details_id() {
		return laptop_details_id;
	}

	public void setLaptop_details_id(Long laptop_details_id) {
		this.laptop_details_id = laptop_details_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFocusr_laptop() {
		return focusr_laptop;
	}

	public void setFocusr_laptop(String focusr_laptop) {
		this.focusr_laptop = focusr_laptop;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAsset_type() {
		return asset_type;
	}

	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}

	public String getPreviously_used_by() {
		return previously_used_by;
	}

	public void setPreviously_used_by(String previously_used_by) {
		this.previously_used_by = previously_used_by;
	}

	public String getLaptop_serial_no() {
		return laptop_serial_no;
	}

	public void setLaptop_serial_no(String laptop_serial_no) {
		this.laptop_serial_no = laptop_serial_no;
	}

	public String getBattery_serial_no() {
		return battery_serial_no;
	}

	public void setBattery_serial_no(String battery_serial_no) {
		this.battery_serial_no = battery_serial_no;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getWindows_product_id() {
		return windows_product_id;
	}

	public void setWindows_product_id(String windows_product_id) {
		this.windows_product_id = windows_product_id;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getAnti_virus_enabled() {
		return anti_virus_enabled;
	}

	public void setAnti_virus_enabled(String anti_virus_enabled) {
		this.anti_virus_enabled = anti_virus_enabled;
	}

	public String getAnti_virus_type() {
		return anti_virus_type;
	}

	public void setAnti_virus_type(String anti_virus_type) {
		this.anti_virus_type = anti_virus_type;
	}

	public String getEmail_configuration() {
		return email_configuration;
	}

	public void setEmail_configuration(String email_configuration) {
		this.email_configuration = email_configuration;
	}

	public String getOutlook_version() {
		return outlook_version;
	}

	public void setOutlook_version(String outlook_version) {
		this.outlook_version = outlook_version;
	}

	public String getDate_of_asset_receipt() {
		return date_of_asset_receipt;
	}

	public void setDate_of_asset_receipt(String date_of_asset_receipt) {
		this.date_of_asset_receipt = date_of_asset_receipt;
	}

	public String getCharger_working() {
		return charger_working;
	}

	public void setCharger_working(String charger_working) {
		this.charger_working = charger_working;
	}

	public String getOther_software_installed() {
		return other_software_installed;
	}

	public void setOther_software_installed(String other_software_installed) {
		this.other_software_installed = other_software_installed;
	}

}
