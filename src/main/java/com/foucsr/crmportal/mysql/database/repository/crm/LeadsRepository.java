package com.foucsr.crmportal.mysql.database.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.crm.Leads;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long>{
	
	
	@Override
    List<Leads> findAll();

	Leads findByFirstName(String firstName); 
	Leads findByLeadOwner(String leadOwner); 
	 
	Boolean existsByFirstName(String firstName);
	Boolean existsByEmail(String email);
	
	
//	 @Query(value = "SELECT * FROM LEADS WHERE STAGE= LEAD",nativeQuery = true)
//	List<Leads> findListOfLeads();
//	
	 @Query(value = "SELECT * FROM LEADS WHERE STAGE = :stage ", nativeQuery = true)
	  List<Leads> findListOfLeads(@Param("stage") String Stage);
	 
	 @Query(value = "SELECT * FROM LEADS WHERE STAGE = :stage "
			 +"AND LEAD_OWNER = :leadOwner", nativeQuery = true)
	  List<Leads> findListOfLeadsForSales(@Param("stage") String Stage,@Param("leadOwner") String leadOwner);
}
