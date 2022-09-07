package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.AssetIssueEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;

@Repository
public interface AssetIssueRepository extends JpaRepository<AssetIssueEntity, Long> {

//	 GET PENDING ISSUE ASSET FOR APPROVAL 
	
//	@Query(value = "SELECT * FROM ASSET_ISSUE WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY ASSET_ISSUE_ID DESC", nativeQuery = true)
//	List<AssetIssueEntity> getPendingIssueAssetForApproval(@Param("manager_id") String manager_id);
	
	@Query(value = "SELECT * FROM ASSET_ISSUE WHERE EMP_ID = :emp_id ORDER BY ASSET_ISSUE_ID DESC", nativeQuery = true)
	List<AssetIssueEntity> getAllAppliedIssueAsset(@Param("emp_id") String emp_id);
	
	@Query(value = "SELECT * FROM ASSET_ISSUE WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ", nativeQuery = true)
	List<AssetIssueEntity> getPendingIssueAssetForApproval(@Param("manager_id") String manager_id);
		
//	@Query(value = "SELECT * FROM ASSET_ISSUE WHERE APPROVER = :managerId AND STATUS = 'SUBMITTED' ", nativeQuery = true)
//	List<AssetIssueEntity> getPendingIssueAssetForApproval(@Param("managerId") String managerId);
	
}





//@Query(value = "SELECT * FROM TIMESHEET WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY TIMESHEET_ID DESC", nativeQuery = true)
//List<TimeSheetEntity> getPendingForApproval(@Param("manager_id") String manager_id);

//@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY LEAVE_APPLIED_ID DESC", nativeQuery = true)
//List<LeaveAppliedEntity> getPendingLeaveForApproval(@Param("manager_id") String manager_id);

//@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE EMP_ID = :emp_id ORDER BY LEAVE_APPLIED_ID DESC", nativeQuery = true)
//List<LeaveAppliedEntity> getAllAppliedLeaves(@Param("emp_id") String emp_id);

//@Query(value = "SELECT * FROM TIMESHEET WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY TIMESHEET_ID DESC", nativeQuery = true)
//List<TimeSheetEntity> getPendingForApproval(@Param("manager_id") String manager_id);
