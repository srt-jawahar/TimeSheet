package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveBalanceEntity;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceEntity, Long> {

	
	@Query(value = "SELECT * FROM LEAVE_BALANCE WHERE USER_ID = :user_id"
			      + " AND LEAVE_MASTER_ID = :leave_master_id "
			      + " AND YEAR = :year ", nativeQuery = true)
	LeaveBalanceEntity findLeaveMasterById(@Param("user_id") Long user_id, 
			                               @Param("leave_master_id") Long leave_master_id,
			                               @Param("year") String year);
	
	
	@Query(value = "SELECT * FROM LEAVE_BALANCE WHERE USER_ID = :user_id"
		      + " AND YEAR = :year ", nativeQuery = true)
   List<LeaveBalanceEntity> getYearlyUserLeaveBalance(@Param("user_id") Long user_id, 
		                                              @Param("year") String year);
	
}
