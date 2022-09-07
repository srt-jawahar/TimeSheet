package com.foucsr.crmportal.mysql.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foucsr.crmportal.mysql.database.model.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long>{
	
	Boolean existsByDesignation(String designation);

}
