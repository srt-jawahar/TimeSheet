package com.foucsr.crmportal.mysql.database.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DEALS_HISTORY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"SNO"})
})
public class DealsHistory {

	@Id
	@SequenceGenerator(name = "DEALS_HISTORY_SEQ", sequenceName = "DEALS_HISTORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEALS_HISTORY_SEQ")
	
	@Column(name = "SNO")
	private Long sNo;
	
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAL_ID", insertable = false, updatable = false)
	private Deals deal;

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

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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

	public String getTimeEdited() {
		return timeEdited;
	}

	public void setTimeEdited(String timeEdited) {
		this.timeEdited = timeEdited;
	}

	@JsonIgnore 
	public Deals getDeal() {
		return deal;
	}

	public void setDeal(Deals deal) {
		this.deal = deal;
	}
	
	
	
}
