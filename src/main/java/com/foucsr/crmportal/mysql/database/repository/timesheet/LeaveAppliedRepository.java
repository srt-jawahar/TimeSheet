package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;

@Repository
public interface LeaveAppliedRepository extends JpaRepository<LeaveAppliedEntity, String> {

	
	@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY LEAVE_APPLIED_ID DESC", nativeQuery = true)
	List<LeaveAppliedEntity> getPendingLeaveForApproval(@Param("manager_id") String manager_id);
	
	@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE EMP_ID = :emp_id ORDER BY LEAVE_APPLIED_ID DESC", nativeQuery = true)
	List<LeaveAppliedEntity> getAllAppliedLeaves(@Param("emp_id") String emp_id);
	
	@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE FROM_DATE = :from_date OR TO_DATE = :to_date ", nativeQuery = true)
	LeaveAppliedEntity getLeaveByDate(@Param("from_date") String from_date,
			                          @Param("to_date") String to_date);
	
}
