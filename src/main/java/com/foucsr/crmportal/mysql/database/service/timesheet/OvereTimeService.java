package com.foucsr.crmportal.mysql.database.service.timesheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.CountryEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.OverTime;
import com.foucsr.crmportal.mysql.database.model.timesheet.OverTimeTask;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.CountryRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.OverTimeRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.OverTimeTaskRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalTimesheet;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.TimesheetStatus;

@Service
public class OvereTimeService {
	
	@Autowired
	private OverTimeRepository overTimeRepository;
	
	@Autowired
	private OverTimeTaskRepository overTimeTaskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@Autowired
    private CountryRepository countryRepository;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(OvereTimeService.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";



	public String getOverTimeId(String userId, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date tsDate = null;
		try {
			tsDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Optional<OverTime> tsEntity = overTimeRepository.findByIDandDate(userId, tsDate);
		String overtimeId = "";
		if (tsEntity.isPresent()) {
			overtimeId = tsEntity.get().getOver_time_id();
		}
		return overtimeId;
	}
	
	public Map<String, Object> initiateProcess(String userId, String date) {

		String id = userId + "_" + date;
		
		// emp id validation in manager mapping
		Map<String, Object> res = new HashMap<String, Object>();
		
		String manager_id = userService.getManager(userId);
		
		if(manager_id == null || "".equals(manager_id)) {
			
			res.put("validation", "error");
			
			return res;
		}

		OverTime otEntity = new OverTime();
		otEntity.setEmpId(userId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate tsLocalDate = null;

		tsLocalDate = LocalDate.parse(date, format);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date tsDate = null;
		try {
			tsDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		otEntity.setDate(tsDate);
		otEntity.setCreatedOn(LocalDateTime.now());
		otEntity.setStatus(TimesheetStatus.DRAFT.getCode());
		otEntity.setApprover(userService.getManager(userId));
		otEntity = overTimeRepository.save(otEntity);

		String overtimeId = otEntity.getOver_time_id();

		
		ArrayList<String> taskdetails = new ArrayList<String>();

		res.put("taskdetails", taskdetails);
		res.put("overtimeId", overtimeId);
		res.put("userId", userId);
		res.put("date", formatter.format(otEntity.getDate()));
		res.put("status", TimesheetStatus.DRAFT.getCode());
		return res;

	}
	
	
	public Map<String, Object> getTaskDetails(String overtimeId) {

		List<OverTimeTask> otEntityList = overTimeTaskRepository.findByOverTimeId(overtimeId);

		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

		Iterator iter = otEntityList.iterator();
		Map<String, Object> taskListDetail = new HashMap<String, Object>();
		List<Map<String, Object>> tasklist = new ArrayList<Map<String, Object>>();

		Optional<OverTime> tsEntity = overTimeRepository.findById(overtimeId);

		if (tsEntity.isPresent()) {
			OverTime entity = tsEntity.get();
			taskListDetail.put("overtimeId", entity.getOver_time_id());
			taskListDetail.put("date", formatter.format(entity.getDate()));
			taskListDetail.put("userId", entity.getEmpId());
			taskListDetail.put("hours", String.valueOf(entity.getHours()));
			taskListDetail.put("minutes", String.valueOf(entity.getMinutes()));
			taskListDetail.put("usercomments", entity.getUserComments());
			taskListDetail.put("managercomments", entity.getManagerComments());
			taskListDetail.put("status", entity.getStatus());
		}

		OverTimeTask taskEntity = null;
		while (iter.hasNext()) {
			taskEntity = (OverTimeTask) iter.next();
			Map<String, Object> taskDetail = new HashMap<String, Object>();

			taskDetail.put("taskId", taskEntity.getOt_task_id());
			taskDetail.put("category", taskEntity.getCategory());
			taskDetail.put("project", taskEntity.getProject());
			taskDetail.put("activity", taskEntity.getActivity());
			taskDetail.put("status", taskEntity.getStatus());
			taskDetail.put("phase", taskEntity.getPhase());
			taskDetail.put("minutes", String.valueOf(taskEntity.getMinutes()));
			taskDetail.put("remarks", taskEntity.getRemarks());

			tasklist.add(taskDetail);

		}
		taskListDetail.put("taskdetails", tasklist);


		return taskListDetail;
	}
	
	
	public boolean saveOverTimeData(Map<String, Object> requestData) {

		boolean flag = false;
		String taskId = (String) requestData.get("taskId");
		String overtimeId = (String) requestData.get("overtimeId");
		String action = (String) requestData.get("action");
		String empId = (String) requestData.get("userId");
		
		Optional<User> userEntity = userRepository.findByEmployeeId(empId);

   		if (!userEntity.isPresent()) {
   			return false;
   		}
   		
   		User user = userEntity.get();
   		String country_code = user.getCountry_code();
   		
   		if(country_code == null 
   				|| "".equals(country_code)) {
   			return false;
   		}
   		
   		CountryEntity country = countryRepository.findCountrybyCode(country_code);
   		Double overTimeHoursForMonth = country.getOvertime_hours() == null ? 0.0 : country.getOvertime_hours();
   		Double overTimeMinutesForMonth = overTimeHoursForMonth * 60; 

		TimesheetStatus status;
		if (action.equalsIgnoreCase("save")) {
			status = TimesheetStatus.DRAFT;
		} else if (action.equalsIgnoreCase("submit")) {
			status = TimesheetStatus.SUBMITTED;
		} else {
			return false;
		}
		Optional<OverTime> tsEntity = overTimeRepository.findById(overtimeId);
		OverTime entity = null;
		if (tsEntity.isPresent()) {
			entity = tsEntity.get();
			entity.setHours(Double.parseDouble((String) requestData.get("hours")));
			entity.setMinutes(Double.parseDouble((String) requestData.get("minutes")));
			entity.setStatus(status.getCode());
			entity.setUserComments((String) requestData.get("usercomments"));
			entity.setUpdatedOn(LocalDateTime.now());

			if (status.equals(TimesheetStatus.SUBMITTED)) {
				entity.setSubmittedOn(LocalDateTime.now());
			}

			ArrayList taskList = new ArrayList();
			taskList = (ArrayList) requestData.get("taskDetails");

			Iterator iter = taskList.iterator();
			Map<String, Object> taskdetail = null;
			while (iter.hasNext()) {
				taskdetail = (Map<String, Object>) iter.next();
				OverTimeTask ottaskEntity = new OverTimeTask();
				String overtimeTaskId = String.valueOf(taskdetail.get("taskId"));
				ottaskEntity.setOt_task_id((overtimeTaskId));
				ottaskEntity.setOvertimeId(entity);
				ottaskEntity.setCategory((String) taskdetail.get("category"));
				ottaskEntity.setProject((String) taskdetail.get("project"));
				ottaskEntity.setActivity((String) taskdetail.get("activity"));
				ottaskEntity.setPhase((String) taskdetail.get("phase"));
				ottaskEntity.setStatus((String) taskdetail.get("status"));
				ottaskEntity.setMinutes(Double.parseDouble(taskdetail.get("minutes").toString()));
				ottaskEntity.setRemarks((String) taskdetail.get("remarks"));
				overTimeTaskRepository.save(ottaskEntity);
			}
			overTimeRepository.save(entity);
		} else {

			return false;
		}


		return true;

	}

	public ResponseEntity<?> getSingleOverTimeById(String overtimeId) {
		if (!(overTimeRepository.existsById(overtimeId))) {

			return new ResponseEntity(new ApiResponse(false, "OverTime with the ID : " + overtimeId + " does not exist"),
					HttpStatus.BAD_REQUEST);

		}

		List<OverTimeTask> taskEntity = overTimeTaskRepository.findByOverTimeId(overtimeId);

		return ResponseEntity.status(HttpStatus.OK).body(taskEntity);
	}

	
	public ResponseEntity<?> getPendingOverTimeForApproval(HttpServletRequest http) {

		
		SCAUtil scaU = new SCAUtil();
		List<OverTime> pendingOverTimes = new ArrayList<OverTime>();
		List<ManagerPendingApprovalTimesheet> managerApproval = new ArrayList<ManagerPendingApprovalTimesheet>();

		try {
			
			Long userId = scaU.getUserIdFromRequest(http, tokenProvider);
			
			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "Approval User is not found !"), HttpStatus.BAD_REQUEST);
			}

			user = opt.get();
			
			String manager_id = user.getEmployeeId();
			
			pendingOverTimes = overTimeRepository.getPendingForApproval(manager_id);
			
			int addMinuteTime = 30;
			int addHoursTime = 5;
			
			  for (OverTime overtimeEntity : pendingOverTimes) {
				  
		        	
        	      User user1 = userRepository.findByEmployeeId(overtimeEntity.getEmpId()).orElseThrow(() -> new AppException("User details does not exist."));
        	      ManagerPendingApprovalTimesheet newProfile = new ManagerPendingApprovalTimesheet();
			       newProfile.setName(user1.getName());
			       newProfile.setApprovedOn(overtimeEntity.getApprovedOn());
			       newProfile.setApprover(overtimeEntity.getApprover());
			       newProfile.setCreatedOn(overtimeEntity.getCreatedOn());
			       
			       Date utcConvertDate = overtimeEntity.getDate();

					utcConvertDate = DateUtils.addHours(utcConvertDate, addHoursTime);
					utcConvertDate = DateUtils.addMinutes(utcConvertDate, addMinuteTime);

					newProfile.setDate(utcConvertDate);
					
			       newProfile.setEmpId(overtimeEntity.getEmpId());
			       newProfile.setHours(overtimeEntity.getHours());
			       newProfile.setId(overtimeEntity.getOver_time_id());
			       newProfile.setManagerComments(overtimeEntity.getManagerComments());
			       newProfile.setMinutes(overtimeEntity.getMinutes());
			       newProfile.setStatus(overtimeEntity.getStatus());
			       newProfile.setSubmittedOn(overtimeEntity.getSubmittedOn());
			       newProfile.setUpdatedOn(overtimeEntity.getUpdatedOn());
			       newProfile.setUserComments(overtimeEntity.getUserComments());
			       
			       List<OverTimeTask> taskEntity = overTimeTaskRepository.findByOverTimeId(overtimeEntity.getOver_time_id());
			       String projects = user1.getName();
			       
			       for (OverTimeTask taskEntitys : taskEntity) {
			    	   
			    	   projects = projects.concat(" ").concat(taskEntitys.getProject());
			    	   
					
				}
			       
			       newProfile.setProjects(projects);
			       
			       
			       
			       
			       
			       managerApproval.add(newProfile);
			       
  			      
		}
			
			if (pendingOverTimes == null) {

				pendingOverTimes = new ArrayList<OverTime>();
			}
			
			
		} catch (Exception e) {


			LOGGER.error("***************** Unable to get pending OverTime list!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get pending OverTime list!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(managerApproval, HttpStatus.OK);

	}

	
	public ResponseEntity<?> approveSelectedOverTimes(List<OverTime> overtimes, HttpServletRequest request) {

		SCAUtil scaU = new SCAUtil();
		
		try {
			
			if (overtimes == null || overtimes.size() == 0) {
				
				return new ResponseEntity(new ApiResponse(false, "Please select atleast one Timesheet to approve!"), HttpStatus.BAD_REQUEST);
			}

			TimesheetStatus status = TimesheetStatus.APPROVED;
			
			List<OverTime> overtimeList = new ArrayList<OverTime>();
			
			for(OverTime overtime : overtimes) {

				Optional<OverTime> otEntity = overTimeRepository.findById(overtime.getOver_time_id());
				OverTime entity = null;
				
				if (otEntity.isPresent()) {
					
					entity = otEntity.get();
					entity.setStatus(status.getCode());
					
					if(overtime.getManagerComments() != null) {
						
						entity.setManagerComments(overtime.getManagerComments());
					}
					
					entity.setUpdatedOn(LocalDateTime.now());
					entity.setApprovedOn(LocalDateTime.now());
					
					overtimeList.add(entity);
					
				}
			}
			
			overTimeRepository.saveAll(overtimeList);
									

		} catch (Exception e) {

			LOGGER.info("***************** Unable to approve Overtime!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to approve Overtime!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);
	}

	
	public boolean approveOverTimeData(Map<String, Object> requestData) {
		// TODO Auto-generated method stub
		String taskId = (String) requestData.get("taskId");
		String overtimeId = (String) requestData.get("overtimeId");

		String action = (String) requestData.get("action");
		String userId = (String) requestData.get("userId");

		TimesheetStatus status = null;
		boolean isApproved = false;


		if (action != null && action.equalsIgnoreCase("approve")) {
			isApproved = true;
			status = TimesheetStatus.APPROVED;
		} else if (action != null && action.equalsIgnoreCase("additional_info")) {
			isApproved = false;
			status = TimesheetStatus.PENDING;
		} else {
			LOGGER.info("Action " + action + " is Not a Valid action for overtime Id" + overtimeId);
			return false;
		}

		Optional<OverTime> tsEntity = overTimeRepository.findById(overtimeId);
		OverTime entity = null;
		if (tsEntity.isPresent()) {
			entity = tsEntity.get();
			entity.setStatus(status.getCode());
			entity.setManagerComments((String) requestData.get("managercomments"));
			entity.setUpdatedOn(LocalDateTime.now());
			entity.setApprover(userId);
			if (status.equals(TimesheetStatus.APPROVED)) {
				entity.setApprovedOn(LocalDateTime.now());
			}
			overTimeRepository.save(entity);

		}
		

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("isApproved", isApproved);


		return true;
	}



}


