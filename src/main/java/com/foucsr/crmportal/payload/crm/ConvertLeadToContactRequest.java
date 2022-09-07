package com.foucsr.crmportal.payload.crm;

import javax.validation.constraints.NotBlank;

public class ConvertLeadToContactRequest {
	
	@NotBlank
	long leadId;
	
	@NotBlank
	String ContactOwner;
	
	@NotBlank
    String accountName;
	
	String department;
	
	String assistant;
	
	String vendorName;
	
	String dob;
	
	String assistantPhone;

	@NotBlank
	String reportingTo;

	public long getLeadId() {
		return leadId;
	}

	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}

	public String getContactOwner() {
		return ContactOwner;
	}

	public void setContactOwner(String contactOwner) {
		ContactOwner = contactOwner;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	
	
}
