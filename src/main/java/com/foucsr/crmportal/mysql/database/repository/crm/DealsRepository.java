package com.foucsr.crmportal.mysql.database.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.crm.Deals;


@Repository
public interface DealsRepository extends JpaRepository<Deals, Long>{

	@Override
    List<Deals> findAll();
	
	Deals findByDealName(String dealName);
	
	boolean existsByDealName(String dealName);
	
	@Query(value = "SELECT DEAL_NAME FROM deals where DEAL_ID =:dealId", nativeQuery = true)
	  String findDealNameByDealId(@Param("dealId") Long id);
	
	
	
	
}
