package com.foucsr.crmportal.mysql.database.repository.crm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.crm.LeadSource;

@Repository
public interface LeadSourceRepository extends JpaRepository<LeadSource, Long>{

	Boolean existsByLeadSourceName(String leadSource);
	
}
