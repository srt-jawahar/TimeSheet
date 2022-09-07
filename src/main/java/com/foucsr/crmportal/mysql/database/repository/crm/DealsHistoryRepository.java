package com.foucsr.crmportal.mysql.database.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.crm.DealsHistory;

@Repository
public interface DealsHistoryRepository extends JpaRepository<DealsHistory, Long> {

	
	@Query(value = "SELECT * FROM deals_history where DEAL_ID =:dealId", nativeQuery = true)
	  List<DealsHistory> findListOfDealHistory(@Param("dealId") Long id);
	
}
