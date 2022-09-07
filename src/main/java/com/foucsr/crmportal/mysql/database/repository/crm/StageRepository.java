package com.foucsr.crmportal.mysql.database.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.crm.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long>{
	
	Boolean existsByStageName(String stageName);
	
	@Query(value = "select PROBABILITY from DEALS_STAGE where STAGE_NAME =:stageName", nativeQuery = true)
	long findProbability(@Param("stageName")List<String> stageName);
	
	

}
