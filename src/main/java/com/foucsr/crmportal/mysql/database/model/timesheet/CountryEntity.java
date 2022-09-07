package com.foucsr.crmportal.mysql.database.model.timesheet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class CountryEntity {

	@Id
	@SequenceGenerator(name = "COUNTRY_SEQ", sequenceName = "COUNTRY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_SEQ")
	@Column(name = "COUNTRY_ID")
	private Long country_id;

	@Column(name = "COUNTRY_NAME")
	private String country_name;

	@Column(name = "COUNTRY_CODE")
	private String country_code;
	
	@Column(name = "OVERTIME_HOURS")
	private Double overtime_hours;

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
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

	public Double getOvertime_hours() {
		return overtime_hours;
	}

	public void setOvertime_hours(Double overtime_hours) {
		this.overtime_hours = overtime_hours;
	}

}
