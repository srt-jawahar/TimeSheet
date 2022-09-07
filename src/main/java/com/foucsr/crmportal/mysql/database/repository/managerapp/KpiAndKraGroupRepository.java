package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;

@Repository
public interface KpiAndKraGroupRepository extends JpaRepository<KpiAndKraGroup, Long>{
	
	List<KpiAndKraGroup> findAll();
	

	Boolean existsByName(String name);
	
	@Transactional
	 @Modifying
	 @Query(value = "DELETE FROM KPIANDKRA_MAP_BY_GROUPS WHERE GROUP_ID =:id ", nativeQuery = true)
	    void deleteByIdInMap(@Param("id") long id);
	
//	@Query(value = "SELECT KpiAndKra_id FROM KPIANDKRA_MAP_BY_GROUPS WHERE GROUP_ID =:id ", nativeQuery = true)
//    List<Long> getByIdInMap(@Param("id") long id);
//	
//	@Query(value = "SELECT DISTINCT kpi_groups.* FROM KPIANDKRA_GROUPS kpi_groups, \r\n"
//			+ "	           KPIANDKRA_MAP_BY_GROUPS kpi_group_map,\r\n"
//			+ "	           KPIANDKRA kpi\r\n"
//			+ "	           WHERE  \r\n"
//			+ "	           kpi_groups.GROUP_ID = kpi_group_map.Group_id and \r\n"
//			+ "	           kpi_group_map.KpiAndKra_id = kpi.KPIANDKRA_ID and\r\n"
//			+ "	           kpi_groups.GROUP_ID = 2", nativeQuery = true)
//	List<KpiAndKra> findListOfKpisUnderGroup(@Param("id") Long id);
	
	@Query(value = "SELECT DISTINCT kpi_groups.* FROM KPIANDKRA_GROUPS kpi_groups,\n" + 
	           "USER_ROLE user\n" +
	           "WHERE \n" + 
	           "kpi_groups.GROUP_ID = user.KPIKRAGROUP_ID and\n" +
	           "user.USER_ID =:userId ", nativeQuery = true)
	KpiAndKraGroup findListOfKpiGroupsforUser(@Param("userId") String userId);

	
	KpiAndKraGroup findByName(String name);
	

}
