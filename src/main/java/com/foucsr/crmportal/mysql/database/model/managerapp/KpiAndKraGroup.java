package com.foucsr.crmportal.mysql.database.model.managerapp;

import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.NotBlank;

import com.foucsr.crmportal.mysql.database.model.User;

@Entity
@Table(name="KPIANDKRA_GROUPS")
public class KpiAndKraGroup {
	
	@Id
	@SequenceGenerator(name = "KPIANDKRA_GROUPS_SEQ", sequenceName = "KPIANDKRA_GROUPS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KPIANDKRA_GROUPS_SEQ")
	@Column(name="GROUP_ID")
	private Long id;
	
	@NotBlank
	@Column(name="GROUP_NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	
	@ManyToMany(cascade =CascadeType.PERSIST)
	//@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "KPIANDKRA_MAP_BY_GROUPS",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "KPIANDKRA_ID"))
    private Set<KpiAndKra> kpiAndKra = new HashSet<>();


	
	
	public KpiAndKraGroup() {
	}

	public KpiAndKraGroup(Long id, @NotBlank String name, String description, Set<KpiAndKra> kpiAndKra) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.kpiAndKra = kpiAndKra;
	}

	public KpiAndKraGroup(@NotBlank String name, String description, Set<KpiAndKra> kpiAndKra) {
		this.name = name;
		this.description = description;
		this.kpiAndKra = kpiAndKra;
	}

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

	public Set<KpiAndKra> getKpiAndKra() {
		return kpiAndKra;
	}

	public void setKpiAndKra(Set<KpiAndKra> kpiAndKra) {
		this.kpiAndKra = kpiAndKra;
	}

	
	
	
	
	
	
	
	
	

	
	
}
