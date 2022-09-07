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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;


@Entity
@Table(name = "LEADS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"EMAIL"}),
       
})
public class Leads {

	@Id
	@SequenceGenerator(name = "LEADS_SEQ", sequenceName = "LEADS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEADS_SEQ")  
	long leadId;
	
	@Column(name="LEAD_OWNER")
	String leadOwner;
	
	@Column(name="COMPANY")
	String company;
	
	@Column(name="FIRST_NAME")
	String firstName;
	@Column(name="LAST_NAME")
	String lastName;
	
	@Column(name="TITLE")
	String title;
	
	@Column(name="PHONE")
	String phone;
	
	@Column(name="MOBILE")
	String mobile;
	
	@Email
	@Column(name="EMAIL")
	String email;
	
	@Column(name="FAX")
	String fax;
	
	@Column(name="WEBSITE")
	String website;
	
	@Column(name="LEAD_SOURCE")
	String leadSource;
	
	@Column(name="LEAD_STATUS")
	String leadStatus; 
	                         
	@Column(name="STAGE")
	String stage;             //lead,archive,contact
	
	@Column(name="INDUSTRY")	                         
	String industry;
	
	@Column(name="NO_OF_EMPLOYEES")
	long noOfEmployees;
	
	@Column(name="ANNUAL_REVENUE")
	double annualRevenue;
	
	@Column(name="RATING")
	String rating;
	
	@Column(name="SKYPE_ID")
	String skypeId;
	@Column(name="SECONDARY_EMAIL")
	String secondaryEmail;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ADDRESS_MAP_BY_LEADS",
    joinColumns = @JoinColumn(name = "leadid"),
    inverseJoinColumns = @JoinColumn(name = "address_id"))
	List<AddressInfo> addressInfo; 
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "FILES_MAP_BY_LEADS",
    joinColumns = @JoinColumn(name = "leadid"),
    inverseJoinColumns = @JoinColumn(name = "fileid"))
	List<FilesInfo> fileInfo; 
   
	@Column(name="DESCRIPTION")
	String description;
	
	
	
	
	@Column(name="CONTACT_OWNER")
	String contactOwner;   // same as leadOwner... 
	                       //  if direct contact, need to be entered by user.
	
	
	@Column(name="ACCOUNT_NAME")
	String accountName;
	
	@Column(name="VENDOR_NAME")
	String vendorName;
	@Column(name="DEPARTMENT")
	String department;
	@Column(name="OTHER_PHONE")
	String otherPhone;
	@Column(name="HOME_PHONE")
	String homePhone;
	@Column(name="ASSISTANT")
	String assistant;
	@Column(name="DOB")
	String dob;
	@Column(name="ASSISTANT_PHONE")
	String assistantPhone;
	@Column(name="REPORTING_TO")
	String reportingTo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SOURCE_MAP_BY_DEALS",
    joinColumns = @JoinColumn(name = "leadId"),
    inverseJoinColumns = @JoinColumn(name = "dealId"))
	List<Deals> deals;    // one to many	       
	                         // one contact to many deals

	public long getLeadId() {
		return leadId;
	}

	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}

	public String getLeadOwner() {
		return leadOwner;
	}

	public void setLeadOwner(String leadOwner) {
		this.leadOwner = leadOwner;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getStage() {
		return stage;
	}
	

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public long getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(long noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public double getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(double annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public List<AddressInfo> getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(List<AddressInfo> addressInfo) {
		this.addressInfo = addressInfo;
	}
	

	public List<FilesInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FilesInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactOwner() {
		return contactOwner;
	}

	public void setContactOwner(String contactOwner) {
		this.contactOwner = contactOwner;
	}
	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAssistantPhone() {
		return assistantPhone;
	}

	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public List<Deals> getDeals() {
		return deals;
	}

	public void setDeals(List<Deals> deals) {
		this.deals = deals;
	}
	
	//List<String> activities;
	
	
}
