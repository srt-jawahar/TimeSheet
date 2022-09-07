package com.foucsr.crmportal.oracle.database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.oracle.database.model.ProjectsOracle;
import com.foucsr.crmportal.oracle.database.repository.ProjectsOracleRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalLeave;
import com.foucsr.crmportal.util.SCAUtil;

@Service
public class ProjectService {

	@Autowired
	private ProjectsOracleRepository projectsOracleRepository;

	Logger log = LoggerFactory.getLogger(ProjectService.class);

	public ResponseEntity<?> getProjects() {

		List<ProjectsOracle> projects = null;
		SCAUtil sca = new SCAUtil();

		try {

			projects = projectsOracleRepository.findAllProjects();

			if (projects == null) {

				projects = new ArrayList<ProjectsOracle>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get projects!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get projects!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(projects, HttpStatus.OK);

	}
	
	public ResponseEntity<?> getProjectById(HttpServletRequest http, Long project_id) {

		ProjectsOracle project = new ProjectsOracle();
		SCAUtil scaU = new SCAUtil();

		try {

			project = projectsOracleRepository.findProjectById(project_id);

		} catch (Exception e) {

			log.error("***************** Unable to get project!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get project!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(project, HttpStatus.OK);

	}


}
