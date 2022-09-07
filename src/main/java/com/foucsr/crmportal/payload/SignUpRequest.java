package com.foucsr.crmportal.payload;

import java.util.List;

import javax.validation.constraints.*;

/**
 * Created by FocusR on 29-Sep-2019.
 */

public class SignUpRequest {
	@NotBlank
	@Size(min = 4, max = 40)
	private String name;

	@NotBlank
	@Size(max = 250)
	private String employeeId;

	@NotBlank
	@Size(min = 3, max = 15)
	private String username;

	@NotBlank
	@Size(max = 100)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

	@NotBlank
	@Size(max = 40)
	private String userRoles;

	private Long id;
	private String full_name;

	@Size(max = 2000)
	private String avatarUrl;

	@NotBlank
	private String managerId;

	@NotBlank
	private String designation;

	@NotNull
	private Long group_id;

	private String country_code;

	private String is_bulk_upload;

	public String getIs_bulk_upload() {
		return is_bulk_upload;
	}

	public void setIs_bulk_upload(String is_bulk_upload) {
		this.is_bulk_upload = is_bulk_upload;
	}

	public Integer getDate_freeze() {
		return date_freeze;
	}

	public void setDate_freeze(Integer date_freeze) {
		this.date_freeze = date_freeze;
	}

	private Integer date_freeze;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

}
