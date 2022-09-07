package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.UsersKpiAndKra;

@Repository
public interface UsersKpiAndKraRepository extends JpaRepository<UsersKpiAndKra, Long>{
	
	
	   @Query(value = "SELECT users.* FROM USERS_KPIANDKRA users WHERE users.USER_ID = :userId AND "
	   		+ "users.DATE_UPDATED LIKE :date% AND"
	   		+ "(users.isUserActive is null or users.isUserActive = 'Y')", nativeQuery = true)
	   UsersKpiAndKra findlistofUsersKpiKraRating(@Param("userId") Long userId,@Param("date") String date);
	   
	   
	   
	   @Query(value = "SELECT * FROM USERS_KPIANDKRA WHERE MANAGER_ID = :manager_id AND STATUS = 'SUBMITTED' \n"
	   		+ "			AND (isUserActive is null or isUserActive = 'Y') \n"
	   		+ "	   		ORDER BY USERS_KPIANDKRA_ID DESC", nativeQuery = true)
		List<UsersKpiAndKra> getPendingKpi(@Param("manager_id") String manager_id);


	   
	   @Query(value = "SELECT users.* FROM USERS_KPIANDKRA users WHERE "
		   		+ "users.DATE_UPDATED LIKE :date% AND"
		   		+ "(users.isUserActive is null or users.isUserActive = 'Y') AND"
		   		+ "(users.status = 'SUBMITTED' or users.status = 'APPROVED')", nativeQuery = true)
	         List<UsersKpiAndKra> findListOfUsersKpiForAMonth(String date);

}
