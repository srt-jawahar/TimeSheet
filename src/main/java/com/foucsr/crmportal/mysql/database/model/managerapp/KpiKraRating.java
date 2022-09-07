package com.foucsr.crmportal.mysql.database.model.managerapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="KPIANDKRA_RATING")
public class KpiKraRating {
	
	@Id
	@SequenceGenerator(name = "KPIANDKRA_RATING_SEQ", sequenceName = "KPIANDKRA_RATING_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KPIANDKRA_RATING_SEQ")
	@Column(name="KPIANDKRA_RATING_ID")
	private Long ratingId;
	
	private String kpi;
	
	@Column(name="description",length = 2000)
	private String description;
	
	private Long rating;
	
	private Long selfrating;
	
	private Long managerRating;

	public Long getRatingId() {
		return ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Long getSelfrating() {
		return selfrating;
	}

	public void setSelfrating(Long selfrating) {
		this.selfrating = selfrating;
	}

	public Long getManagerRating() {
		return managerRating;
	}

	public void setManagerRating(Long managerRating) {
		this.managerRating = managerRating;
	}	
	
	
	

}
