package com.foucsr.crmportal.mysql.database.repository.managerapp;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;


@Repository
public interface KpiAndKraRepository extends JpaRepository<KpiAndKra, Long>{
	
	List<KpiAndKra> findAll();
	
	Boolean existsByName(String name);
	
	
	@Query(value = "SELECT DISTINCT kpi.* FROM KPIANDKRA kpi,\n"
			+ "	           KPIANDKRA_MAP_BY_GROUPS kpi_group_map\n"
			+ "	           WHERE kpi_group_map.Group_id =:id and\n"
			+ "	           kpi.KPIANDKRA_ID = kpi_group_map.KpiAndKra_id \n"
			+ "	           order by kpi.KPIANDKRA_ID;", nativeQuery = true)
	List<KpiAndKra> findListOfKpisUnderGroup(@Param("id") long id);
	
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM KPIANDKRA_MAP_BY_GROUPS WHERE KPIANDKRA_ID =:id ", nativeQuery = true)
	    void deleteByIdInMap(@Param("id") long id);

	

}
