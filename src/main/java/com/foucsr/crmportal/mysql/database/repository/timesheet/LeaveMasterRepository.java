package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;

@Repository
public interface LeaveMasterRepository extends JpaRepository<LeaveMasterEntity, Long> {

	
	@Query(value = "SELECT * FROM LEAVE_MASTER WHERE LEAVE_MASTER_ID = :leave_master_id"
			+ " AND IS_ACTIVE = 'Y' ", nativeQuery = true)
	LeaveMasterEntity findLeaveMasterById(@Param("leave_master_id") Long leave_master_id);
	
	
	@Query(value = "SELECT * FROM LEAVE_MASTER WHERE COUNTRY_CODE = :country_code"
			+ " AND IS_ACTIVE = 'Y' ", nativeQuery = true)
	List<LeaveMasterEntity> findLeaveMasterByCountry(@Param("country_code") String country_code);
}
