package com.foucsr.crmportal.mysql.database.controller.managerapp;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.mysql.database.service.managerapp.ManagersService;

@RestController
@RequestMapping("/api/Manager/Service")
public class ManagersController {

	@Autowired
	private ManagersService kpiAndKraService;
	
	
	@GetMapping("/getManagerList")
	public ResponseEntity<?> getManagerList(@RequestParam Map<String, String> requestParams, Principal principal) {

		ResponseEntity<?> message = kpiAndKraService.getManagers();

		return message;
	}

}
