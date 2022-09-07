package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetUserEntity;

public interface AssetUserRepository extends JpaRepository<AssetUserEntity, Long> {
	
	@Query(value = "SELECT * FROM ASSET_USER_DETAILS WHERE ASSET_ID = :asset_id ORDER BY ASSET_USER_ID DESC LIMIT 1 ",nativeQuery = true)
	AssetUserEntity getLatestAssetUserDetails(@Param ("asset_id")Long assetId);



}
