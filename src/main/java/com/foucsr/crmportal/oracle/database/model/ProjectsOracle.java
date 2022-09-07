package com.foucsr.crmportal.oracle.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PA_PROJECTS_ALL")
public class ProjectsOracle {

	@Id
	@Column(name = "PROJECT_ID")
	private Long project_id;

	@Column(name = "SEGMENT1")
	private String segment1;

	@Column(name = "NAME")
	private String name;

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}