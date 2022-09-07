package com.foucsr.crmportal.payload;

import java.time.Instant;

import javax.validation.constraints.Size;

public class UserProfile {
	private Long id;
	private String username;
	private String name;
	private String employeeId;
	private String email;
	private Long roleId;
	private String roleDescription;
	private String avatarUrl;
	private char isActive;
	private String managerId;
	private String groupName;
	private String designation;
	private String country_code;
	private Long groupId;
	private String is_bulk_upload;
	private Integer date_freeze;

	public UserProfile(Long id, String username, String name, String employeeId, String email, String avatarUrl) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.employeeId = employeeId;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public UserProfile(Long id, String username, String name, String employeeId, String email, Long roleId,
			String roleDescription, String avatarUrl, char isActive, String managerId, String groupName,
			String designation, Long groupId, String country_code, String is_bulk_upload, Integer date_freeze) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.employeeId = employeeId;
		this.email = email;
		this.roleId = roleId;
		this.roleDescription = roleDescription;
		this.avatarUrl = avatarUrl;
		this.isActive = isActive;
		this.managerId = managerId;
		this.groupName = groupName;
		this.designation = designation;
		this.groupId = groupId;
		this.country_code = country_code;
		this.is_bulk_upload = is_bulk_upload;
		this.date_freeze= date_freeze;
	}

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

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

}
