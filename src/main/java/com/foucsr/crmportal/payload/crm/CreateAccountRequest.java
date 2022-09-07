package com.foucsr.crmportal.payload.crm;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.foucsr.crmportal.mysql.database.model.crm.AddressInfo;

public class CreateAccountRequest {

    long accountId;
	
    @NotBlank
	String accountOwner;           
	
    @NotBlank
	String accountName; 
    
	@NotBlank
	String accountNumber;
	
	@NotBlank
	String accountType;
	
	String accountSite;
	
	String parentAccoount;

	String industry;

	String annualRevenue;

	String rating;

	String phone;

	String website;

	String ownership;

	long employees;

	String sicCode;

	String description;
	
	List<AddressInfo> addressInfo;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountSite() {
		return accountSite;
	}

	public void setAccountSite(String accountSite) {
		this.accountSite = accountSite;
	}

	public String getParentAccoount() {
		return parentAccoount;
	}

	public void setParentAccoount(String parentAccoount) {
		this.parentAccoount = parentAccoount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(String annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public long getEmployees() {
		return employees;
	}

	public void setEmployees(long employees) {
		this.employees = employees;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<AddressInfo> getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(List<AddressInfo> addressInfo) {
		this.addressInfo = addressInfo;
	}
	
}
