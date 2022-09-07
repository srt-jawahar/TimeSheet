package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="KPIANDKRA")
public class KpiAndKra {
	

	@Id
	@SequenceGenerator(name = "KPIANDKRA_SEQ", sequenceName = "KPIANDKRA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KPIANDKRA_SEQ")
	@Column(name="KPIANDKRA_ID")
	private Long id;
	 
	@NotBlank
	@Column(name="KPIANDKRA_NAME",length = 2000)
	private String name;
	
	@NotBlank	
	@Column(name="KPI_DESCRIPTION",length = 2000)
	private String description;

	@NotNull
	@Column(name="RATING")
	private Long rating;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	
	
	
	
	
	 
	 
	
	

}
