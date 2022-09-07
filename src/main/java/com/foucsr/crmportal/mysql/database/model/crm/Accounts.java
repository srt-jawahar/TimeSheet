package com.foucsr.crmportal.mysql.database.model.crm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "ACCOUNTS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ACCOUNT_NAME"})
})
public class Accounts {

	
	//mapping with source (while converting to contact)
	//one to many
	//one company to many contacts
	@Id
	@SequenceGenerator(name = "Accounts_SEQ", sequenceName = "Accounts_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Accounts_SEQ")  
	@Column(name="ACCOUNT_ID")
	long accountId;
	
	@Column(name="ACCOUNT_OWNER")
	String accountOwner;           
	
	@Column(name="ACCOUNT_NAME")
	String accountName; 
	
	@Column(name="ACCOUNT_SITE")
	String accountSite;
	
	@Column(name="PARENT_ACCOUNT")
	String parentAccoount;
	
	@Column(name="ACCOUNT_NUMBER")
	String accountNumber;
	
	@Column(name="ACCOUNT_TYPE")
	String accountType;
	
	@Column(name="INDUSTRY")
	String industry;
	
	@Column(name="ANNUAL_REVENUE")
	String annualRevenue;
	
	@Column(name="RATING")
	String rating;
	
	@Column(name="PHONE")
	String phone;
	
	@Column(name="WEBSITE")
	String website;
	
	@Column(name="OWNERSHIP")
	String ownership;
	
	@Column(name="EMPLOYEES")
	long employees;
	
	@Column(name="SIC_CODE")
	String sicCode;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ADDRESS_MAP_BY_ACCOUNTS",
    joinColumns = @JoinColumn(name = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "address_id"))
	List<AddressInfo> addressInfo; 
	

	@Column(name="DESCRIPTION")
	String description;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ACCOUNTS_MAP_BY_LEADS",
    joinColumns = @JoinColumn(name = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "leadId"))
	List<Leads> leads;      
	

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

	public void setAnnualRevenue(String annualrevenue) {
		this.annualRevenue = annualrevenue;
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

	public List<Leads> getLeads() {
		return leads;
	}

	public void setLeads(List<Leads> leads) {
		this.leads = leads;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public List<AddressInfo> getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(List<AddressInfo> addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
