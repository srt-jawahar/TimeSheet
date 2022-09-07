package com.foucsr.crmportal.mysql.database.controller.managerapp;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.mysql.database.model.managerapp.LaptopEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.managerapp.LaptopService;

@RestController
@RequestMapping("/api/Assets/Service")
public class LaptopController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private LaptopService laptopService;

	@GetMapping("/getLaptopLOVList")
	public ResponseEntity<?> getLaptopLOVList(Principal principal) {

		ResponseEntity<?> message = laptopService.getLaptopLOVList();

		return message;
	}
	
	
	@PostMapping("/createLaptopDetails")
	public ResponseEntity<?> createLaptopDetails(HttpServletRequest http, @Valid @RequestBody LaptopEntity laptop,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> leaveMasterResp = laptopService.createLaptopDetails(laptop, http);

		return leaveMasterResp;
	}
	
	@GetMapping("/getListOfLaptopDetails")
	public ResponseEntity<?> getListOfLaptopDetails(Principal principal) {

		ResponseEntity<?> message = laptopService.getListOfLaptopDetails();

		return message;
	}
	
	
	

}
