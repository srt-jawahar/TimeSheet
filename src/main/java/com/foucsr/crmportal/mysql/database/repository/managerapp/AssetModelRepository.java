package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;

@Repository
public interface AssetModelRepository extends JpaRepository<AssetModelEntity, Long> {

	@Query(value = " SELECT * FROM ASSET_DETAILS where ASSET_ID = :asset_id ", nativeQuery = true)
	List<AssetModelEntity> getAssetDetailsById(@Param("asset_id") Long asset_id);
	
	 @Query(value = "  SELECT * FROM ASSET_DETAILS where USER_ID = :userId", nativeQuery = true)
	 List<AssetModelEntity> getUserAssetDetails(@Param ("userId")Long userId);

}



//@Query(value = "SELECT * FROM LEAVE_APPLIED_TBL WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY LEAVE_APPLIED_ID DESC", nativeQuery = true)
//List<LeaveAppliedEntity> getPendingLeaveForApproval(@Param("manager_id") String manager_id);
