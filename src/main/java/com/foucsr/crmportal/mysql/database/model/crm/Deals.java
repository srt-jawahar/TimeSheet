package com.foucsr.crmportal.mysql.database.model.crm;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "DEALS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"DEAL_NAME"})
})
public class Deals {

	
	 @Id
	 @SequenceGenerator(name = "DEALS_SEQ", sequenceName = "DEALS_SEQ", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEALS_SEQ")  
	
	
	 @Column(name = "DEAL_ID")
	 private Long dealId;
	
	@Column(name="DEAL_OWNER")
	private String dealOwner;
	 
	@Column(name="DEAL_NAME")
	private String dealName;
	
	@Column(name="ACCOUNT_NAME")
	private String accountName;
	
	@Column(name="TYPE")
	private String dealType;
	
	@Column(name="NEXT_STEP")
	private String nextStep;
	
	@Column(name="LEAD_SOURCE")
	private String leadSource;
	
	@Column(name="CONTACT_NAME")
	private String contactName;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="CLOSING_DATE")
	private String closingDate;
	
	@Column(name="STAGE")
	private String stage;

	
	@Column(name="PROBABILITY")
	private double probability;
	
	@Column(name="EXPECTED_REVENUE")
	private double expectedRevenue;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="TIME_EDITED")
	private String timeEdited;
	

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "deal")	
	List<DealsHistory> dealsHistory; 


	public List<DealsHistory> getDealsHistory() {
		return dealsHistory;
	}

	public void setDealsHistory(List<DealsHistory> dealsHistory) {
		this.dealsHistory = dealsHistory;
	}

	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}

	public String getDealOwner() {
		return dealOwner;
	}

	public void setDealOwner(String dealOwner) {
		this.dealOwner = dealOwner;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTimeEdited() {
		return timeEdited;
	}

	public void setTimeEdited(String timeEdited) {
		this.timeEdited = timeEdited;
	}

	
	
	
	
	
	
}
