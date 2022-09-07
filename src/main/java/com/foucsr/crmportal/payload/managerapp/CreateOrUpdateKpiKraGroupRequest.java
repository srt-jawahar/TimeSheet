package com.foucsr.crmportal.payload.managerapp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateOrUpdateKpiKraGroupRequest {
	
	private Long id;
	 
	private String name;
		
	private String description;
	
	private Long[] kpiIds;

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

	public Long[] getKpiIds() {
		return kpiIds;
	}

	public void setKpiIds(Long[] kpiIds) {
		this.kpiIds = kpiIds;
	}

	
	
	

}
