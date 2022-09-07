package com.foucsr.crmportal.oracle.database.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.oracle.database.service.ProjectService;

@RestController
@RequestMapping("/api/Project/Service")
@CrossOrigin
public class ProjectsController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/getAllProjects")
	public ResponseEntity<?> getProjects(Principal principal) {

		ResponseEntity<?> message = projectService.getProjects();

		return message;
	}
	
	@GetMapping("/getProjectDetails")
	public ResponseEntity<?> getProjectDetails(HttpServletRequest http,@RequestParam Map<String, String> requestParams, Principal principal) {

		Long value = Long.parseLong(requestParams.get("project_id"));
		ResponseEntity<?> message = projectService.getProjectById(http, value);

		return message;
	}
}
