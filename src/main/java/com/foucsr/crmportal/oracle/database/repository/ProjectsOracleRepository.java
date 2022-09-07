package com.foucsr.crmportal.oracle.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.oracle.database.model.ProjectsOracle;

@Repository
public interface ProjectsOracleRepository extends CrudRepository<ProjectsOracle, Long> {

	@Query(value = "SELECT * FROM PA_PROJECTS_ALL", nativeQuery = true)
	List<ProjectsOracle> findAllProjects();

	
	@Query(value = "SELECT * FROM PA_PROJECTS_ALL WHERE PROJECT_ID = :project_id", nativeQuery = true)
	ProjectsOracle findProjectById(@Param("project_id") Long project_id);

}