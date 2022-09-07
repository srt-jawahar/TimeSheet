package com.foucsr.crmportal.mysql.database.controller.timesheet;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.managerapp.UsersKpiAndKra;
import com.foucsr.crmportal.mysql.database.model.timesheet.OverTime;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.OverTimeRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.timesheet.OvereTimeService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.security.UserPrincipal;
import com.foucsr.crmportal.util.OverTimeDelegator;
import com.foucsr.crmportal.util.Request;
import com.foucsr.crmportal.util.Response;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.TimesheetDelegator;

@RestController
@RequestMapping("/api/OverTime/Service")
public class OverTimeController {
	
	@Autowired
	private OverTimeRepository overTimeRepository;
	
	@Autowired
	private OvereTimeService overeTimeService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	SCAUtil scaUtil;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private OverTimeDelegator overtimeDelegator;
	
	
	@PostMapping(path = "/initiate", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response initateTimeSheet(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = overtimeDelegator.initateTimeSheet(request.getRequestData());
		return res;
	}
	
	@PostMapping(path = "/submit", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response submitOverTime(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = overtimeDelegator.submitOverTime(request.getRequestData());
		return res;
	}
	
	@PostMapping(path = "/approvaltask", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response gettaskdetails(Authentication authentication, @RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = overtimeDelegator.getTimesheetTaskDetails(request.getRequestData());
		return res;
	}
	
	@GetMapping("/getSingleOverTimeById/{overtimeId}")
	public ResponseEntity<?> getSingleOverTime(@PathVariable String overtimeId, Principal principal)
			throws AppException {

		ResponseEntity<?> message = overeTimeService.getSingleOverTimeById(overtimeId);

		return message;

	}
	
	@GetMapping("/getOverTimeForApproval")
	public ResponseEntity<?> getOverTimeForApproval(HttpServletRequest http, Principal principal) {

		ResponseEntity<?> message = overeTimeService.getPendingOverTimeForApproval(http);

		return message;
	}
	
	@PutMapping("/bulkApprove")
	public ResponseEntity<?> bulkApprove(HttpServletRequest request,
			@Valid @RequestBody List<OverTime> lines, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> message = overeTimeService.approveSelectedOverTimes(lines, request);

		return message;
	}
	
	// single overtime approve
	@PostMapping(path = "/complete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response approveTimesheet(Authentication authentication, @RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
		request.getRequestData().put("userId", up.getEmployeeId());
		Response res = overtimeDelegator.approveOverTime(request.getRequestData());
		return res;
	}

}
