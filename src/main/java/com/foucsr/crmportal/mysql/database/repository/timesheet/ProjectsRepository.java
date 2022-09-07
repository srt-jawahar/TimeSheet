package com.foucsr.crmportal.mysql.database.repository.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foucsr.crmportal.mysql.database.model.timesheet.Projects;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}
