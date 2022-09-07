package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.foucsr.crmportal.mysql.database.model.User;

@Entity
@Table(name="USERS_KPIANDKRA")
public class UsersKpiAndKra {
	
	@Id
	@SequenceGenerator(name = "USERS_KPIANDKRA_SEQ", sequenceName = "USERS_KPIANDKRA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_KPIANDKRA_SEQ")
	@Column(name="USERS_KPIANDKRA_ID")
	private Long id;
	
	@Column(name="USER_ID")   
	private Long userId;
	
	@NotBlank
	@Column(name="MANAGER_ID")   
	private String managerId;
	
//	@ManyToOne()
//	@JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)    
//	private User user;

	
	private char isUserActive;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USERS_KPI_KRA_MAP_BY_KPI_KRA_RATING",
    joinColumns = @JoinColumn(name = "Kpi_Details_id"),
    inverseJoinColumns = @JoinColumn(name = "rating_id"))
	private List<KpiKraRating> kpiList;
	
	@NotBlank
	private String status;     // "DRAFT" or "SUBMITTED" or "APPROVED"
	
	@Column(name="DATE_UPDATED")
	private String date;
	
	@Column(name="DATE_SUBMITTED")
	private String submittedDate;
	
	@Column(name="DATE_APPROVED")
	private String approvedDate;
	
	
	
	

public UsersKpiAndKra() {
	}

public UsersKpiAndKra(Long id, @NotNull Long userId, char isUserActive, List<KpiKraRating> kpiList,
			@NotBlank String status, String date) {
		this.id = id;
		this.userId = userId;
		this.isUserActive = isUserActive;
		this.kpiList = kpiList;
		this.status = status;
		this.date = date;
	}

public UsersKpiAndKra(Long userId, char isUserActive, List<KpiKraRating> kpiList, String status, String date,String managerId) {
		this.userId = userId;
		this.isUserActive = isUserActive;
		this.kpiList = kpiList;
		this.status = status;
		this.date = date;
		this.managerId = managerId;
	}

//	public UsersKpiAndKra(User user, char isUserActive, List<KpiKraRating> kpiList, String status, String date) {
//		this.user = user;
//		this.isUserActive = isUserActive;
//		this.kpiList = kpiList;
//		this.status = status;
//		this.date = date;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public List<KpiKraRating> getKpiList() {
		return kpiList;
	}

	public void setKpiList(List<KpiKraRating> kpiList) {
		this.kpiList = kpiList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public char getIsUserActive() {
		return isUserActive;
	}

	public void setIsUserActive(char isUserActive) {
		this.isUserActive = isUserActive;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	
	
	

	
		
	
	

}
