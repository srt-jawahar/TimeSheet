package com.foucsr.crmportal.mysql.database.controller.timesheet;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.timesheet.LeaveService;
import com.foucsr.crmportal.util.LeaveAssignUserRequest;

@RestController
@RequestMapping("/api/Leave/Service")
public class LeaveController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private LeaveService leaveService;
	
	
	@PostMapping("/createOrUpdateLeaveMaster")
	public ResponseEntity<?> createOrUpdateLeaveMaster(HttpServletRequest http, @Valid @RequestBody LeaveMasterEntity leaveMaster,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> leaveMasterResp = leaveService.createOrUpdateLeaveMaster(leaveMaster, http);

		return leaveMasterResp;
	}

	@GetMapping("/getCountries")
	public ResponseEntity<?> getCountries(Principal principal) {

		ResponseEntity<?> message = leaveService.getCountries();

		return message;
	}
	
	@PostMapping("/assignLeaveCategoryToUser")
	public ResponseEntity<?> assignLeaveCategoryToUser(HttpServletRequest http ,@Valid @RequestBody List<LeaveAssignUserRequest> usersCountries,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> message = leaveService.assignLeaveCategoryToUser(usersCountries , http);

		return message;
	}
	
	
	@PostMapping("/applyLeave")
	public ResponseEntity<?> applyLeave(HttpServletRequest http, @Valid @RequestBody LeaveAppliedEntity leaveMaster,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> leaveMasterResp = leaveService.applyLeave(leaveMaster, http);

		return leaveMasterResp;
	}
	
	@GetMapping("/getDataToApplyLeave")
	public ResponseEntity<?> getDataToApplyLeave(HttpServletRequest http, Principal principal) {

		ResponseEntity<?> message = leaveService.getDataToApplyLeave(http);

		return message;
	}
	
	@GetMapping("/getPendingLeaveForApproval")
	public ResponseEntity<?> getPendingLeaveForApproval(HttpServletRequest http,@RequestParam Map<String, String> requestParams, Principal principal) {

		String value = requestParams.get("value");
		ResponseEntity<?> message = leaveService.getPendingLeaveForApproval(http, value);

		return message;
	}
	
	
	@PutMapping("/approveOrRejectLeave")
	public ResponseEntity<?> bulkApprove(HttpServletRequest request,
			@Valid @RequestBody List<LeaveAppliedEntity> lines, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> message = leaveService.approveOrRejectLeave(lines, request);

		return message;
	}
	
	@GetMapping("/getLeaveMaster")
	public ResponseEntity<?> getLeaveMaster(Principal principal) {

		ResponseEntity<?> message = leaveService.getLeaveMaster();

		return message;
	}
	

}
