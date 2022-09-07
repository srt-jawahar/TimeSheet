package com.foucsr.crmportal.mysql.database.service.timesheet;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.DateFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.BadRequestException;
import com.foucsr.crmportal.exception.ExcelException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.TimeSheetRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.TimeSheetTaskRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.TimeSheetUpload;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalTimesheet;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.security.UserPrincipal;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.MailContentUtil;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.TimesheetStatus;

/**
 * @author Muthuganesh
 * 
 */

@Service
public class TimesheetService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TimesheetService.class);
	private static final String PROCESS_NAME = "timesheetProcess";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_FORMAT_DDMMMYY = "dd-MMM-yy";

	@Autowired
	private TimeSheetRepository timesheetRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private TimeSheetTaskRepository timesheetTaskRepo;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	public String getTimesheetId(String userId, String date) {
		LOGGER.info("getTimesheetId Request :: Started for userid:" + userId + " with Date :" + date);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date tsDate = null;
		try {
			tsDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Optional<TimeSheetEntity> tsEntity = timesheetRepo.findByIDandDate(userId, tsDate);
	
		String timesheetId = "";
		
		if (tsEntity.isPresent()) {
			Double count = timesheetRepo.findCountByIDandDate(userId, tsDate);
			if(count > 1.0) {
				throw new BadRequestException(
						"Timesheet with this date is already entered");
			}
			timesheetId = tsEntity.get().getId();
		}
		
		LOGGER.info("getTimesheetId Request :: Completed");
		return timesheetId;
	}

	public Map<String, Object> initiateProcess(String userId, String date) {

		LOGGER.info("initiateProcess Request :: Started for userid:" + userId + " with Date :" + date);
//		VariableMap vm = Variables.createVariables();
		String id = userId + "_" + date;
		LOGGER.info("Timesheet Process" + id);
//		vm.put("userId", userId);
//		vm.put("date", date);
		
		// emp id validation in manager mapping
		Map<String, Object> res = new HashMap<String, Object>();
		
		// validate the laptop submission 
		User user = null;
		Optional<User> userOpt = userRepository.findByEmployeeId(userId);
		if (userOpt.isPresent()) {
			user = userOpt.get();
			
			if(user.getIs_laptop_details_submitted() == null
					|| "N".equals(user.getIs_laptop_details_submitted())) {
				
				res.put("validation", "laptop_error");
				return res;
			}
		}
		
		String manager_id = userService.getManager(userId);

		if (manager_id == null || "".equals(manager_id)) {

			res.put("validation", "error");

			return res;
		}

		TimeSheetEntity tsEntity = new TimeSheetEntity();
		tsEntity.setEmpId(userId);
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

		tsEntity.setDate(tsDate);
		tsEntity.setCreatedOn(LocalDateTime.now());
		tsEntity.setStatus(TimesheetStatus.DRAFT.getCode());
		tsEntity.setApprover(userService.getManager(userId));
		tsEntity.setCalendar(null);
		tsEntity = timesheetRepo.save(tsEntity);

		String timesheetId = tsEntity.getId();
//		vm.put("timesheetId", timesheetId);

//		ProcessInstance pi = runtimeService.startProcessInstanceByKey(PROCESS_NAME, timesheetId, vm);

//		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

//		String taskId = task.getId();
//		task.setAssignee(userId);

		ArrayList<String> taskdetails = new ArrayList<String>();
//		res.put("taskId", taskId);
		res.put("taskdetails", taskdetails);
		res.put("timesheetId", timesheetId);
		res.put("userId", userId);
		res.put("date", formatter.format(tsEntity.getDate()));
		res.put("status", TimesheetStatus.DRAFT.getCode());
		LOGGER.info("initiateProcess Request :: Completed for userid:" + userId + " with Date :" + date);
		return res;

	}

	public boolean saveTimesheetData(Map<String, Object> requestData) {

		LOGGER.info("saveTimesheetData Request :: Started ");
		boolean flag = false;
		String taskId = (String) requestData.get("taskId");
		String timesheetId = (String) requestData.get("timesheetId");
		String Calendar =(String)requestData.get("calendar");
		String action = (String) requestData.get("action");
		String userId = (String)requestData.get("userId");

		LOGGER.info("saveTimesheetData Request :: timesheetId " + timesheetId + " Action:: " + action + " ::");
		TimesheetStatus status;
		if (action.equalsIgnoreCase("save")) {
			status = TimesheetStatus.DRAFT;
		} else if (action.equalsIgnoreCase("submit")) {
			status = TimesheetStatus.SUBMITTED;
		} else {
			LOGGER.info("Staus not Valid for Timesheet ID " + timesheetId);
			return false;
		}
		Optional<TimeSheetEntity> tsEntity = timesheetRepo.findById(timesheetId);
		TimeSheetEntity entity = null;
		if (tsEntity.isPresent()) {
			entity = tsEntity.get();
			entity.setHours(Double.parseDouble((String) requestData.get("hours")));
			entity.setMinutes(Double.parseDouble((String) requestData.get("minutes")));
			entity.setStatus(status.getCode());
			entity.setUserComments((String) requestData.get("usercomments"));
			entity.setUpdatedOn(LocalDateTime.now());
			entity.setCalendar(Calendar);
			entity.setApprover(userService.getManager(userId));
			LOGGER.info(
					"saveTimesheetData Request :: timesheetId " + timesheetId + " date:: " + entity.getDate() + " ::");

			if (status.equals(TimesheetStatus.SUBMITTED)) {
				entity.setSubmittedOn(LocalDateTime.now());
			}

			ArrayList taskList = new ArrayList();
			taskList = (ArrayList) requestData.get("taskDetails");

			Iterator iter = taskList.iterator();
			Map<String, Object> taskdetail = null;
			while (iter.hasNext()) {
				taskdetail = (Map<String, Object>) iter.next();
				TimesheetTaskEntity tstaskEntity = new TimesheetTaskEntity();
				String timesheetTaskId = String.valueOf(taskdetail.get("taskId"));
				LOGGER.info(
						"timesheetTaskId from Screen for timesheetId: " + timesheetId + " Task id:" + timesheetTaskId);
				tstaskEntity.setId(timesheetTaskId);
				tstaskEntity.setTimesheetId(entity);
				tstaskEntity.setCategory((String) taskdetail.get("category"));
				tstaskEntity.setProject((String) taskdetail.get("project"));
				tstaskEntity.setActivity((String) taskdetail.get("activity"));
				tstaskEntity.setPhase((String) taskdetail.get("phase"));
				tstaskEntity.setStatus((String) taskdetail.get("status"));
				tstaskEntity.setMinutes(Double.parseDouble(taskdetail.get("minutes").toString()));
				tstaskEntity.setRemarks((String) taskdetail.get("remarks"));
				timesheetTaskRepo.save(tstaskEntity);
			}
			timesheetRepo.save(entity);
		} else {

			LOGGER.info("Timesheet ID not availabe in DB " + timesheetId);
			return false;
		}

		/*
		 * if (status.equals(TimesheetStatus.SUBMITTED)) {
		 * 
		 * Task task =
		 * taskService.createTaskQuery().processInstanceBusinessKey(timesheetId).
		 * singleResult();
		 * 
		 * taskService.complete(task.getId());
		 * 
		 * }
		 */

		LOGGER.info("saveTimesheetData Request :: Completed for timesheetId " + timesheetId + " Action:: " + action
				+ " ::");

		return true;

	}

	/*
	 * public Map<String, Object> getTaskList(String userId) { // TODO
	 * Auto-generated method stub
	 * 
	 * LOGGER.info("getTaskList Request ::" + userId + " Started"); List<Task>
	 * taskList = taskService.createTaskQuery().taskAssignee(userId).list();
	 * DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT_DDMMMYY);
	 * SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DDMMMYY);
	 * Iterator iter = taskList.iterator(); Map<String, Object> taskListDetail = new
	 * HashMap<String, Object>(); List<Map<String, Object>> tasklist = new
	 * ArrayList<Map<String, Object>>(); Task task = null; while (iter.hasNext()) {
	 * task = (Task) iter.next(); Map<String, Object> taskDetail = new
	 * HashMap<String, Object>();
	 * 
	 * taskDetail.put("taskId", task.getId()); taskDetail.put("taskName",
	 * task.getName()); taskDetail.put("processInstanceId",
	 * task.getProcessInstanceId());
	 * 
	 * String timesheetId = (String)
	 * runtimeService.getVariable(task.getExecutionId(), "timesheetId");
	 * taskDetail.put("timesheetId", timesheetId);
	 * LOGGER.info("getTaskList TaskDetails from Camunda ::taskId :" + task.getId()
	 * + " ::taskName :" + task.getName() + " ::timesheetId :" + timesheetId);
	 * Optional<TimeSheetEntity> tsEntity = timesheetRepo.findById(timesheetId);
	 * TimeSheetEntity entity = null; if (tsEntity.isPresent()) { entity =
	 * tsEntity.get();
	 * 
	 * taskDetail.put("date", formatter.format(entity.getDate()));
	 * taskDetail.put("userId", entity.getEmpId()); taskDetail.put("name",
	 * userService.getUserName(entity.getEmpId())); taskDetail.put("status",
	 * entity.getStatus()); }
	 * 
	 * tasklist.add(taskDetail);
	 * 
	 * } taskListDetail.put("taskdetails", tasklist);
	 * 
	 * LOGGER.info("getTaskList Request ::" + userId + " Completed");
	 * 
	 * return taskListDetail;
	 * 
	 * }
	 */
	public boolean approveTimesheetData(Map<String, Object> requestData) {
		// TODO Auto-generated method stub
		String taskId = (String) requestData.get("taskId");
		String timesheetId = (String) requestData.get("timesheetId");

		String action = (String) requestData.get("action");
		String userId = (String) requestData.get("userId");

		LOGGER.info("approveTimesheetData Request :: started for timesheetId::" + timesheetId + " :Action ::" + action);
		String sendNotification = (String) requestData.get("sendNotificationsTo");
		TimesheetStatus status = null;
		boolean isApproved = false;

		LOGGER.info("sendNotificationsTo :" + sendNotification);

		if (action != null && action.equalsIgnoreCase("approve")) {
			isApproved = true;
			status = TimesheetStatus.APPROVED;
		} else if (action != null && action.equalsIgnoreCase("additional_info")) {
			isApproved = false;
			status = TimesheetStatus.PENDING;
		} else {
			LOGGER.info("Action " + action + " is Not a Valid action for timesheet Id" + timesheetId);
			return false;
		}

		Optional<TimeSheetEntity> tsEntity = timesheetRepo.findById(timesheetId);
		TimeSheetEntity entity = null;
		if (tsEntity.isPresent()) {
			entity = tsEntity.get();
			entity.setStatus(status.getCode());
			entity.setManagerComments((String) requestData.get("managercomments"));
			entity.setUpdatedOn(LocalDateTime.now());
			entity.setNotifiedTo(sendNotification);
			entity.setApprover(userId);
			if (status.equals(TimesheetStatus.APPROVED)) {
				entity.setApprovedOn(LocalDateTime.now());
			}
			timesheetRepo.save(entity);

		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("isApproved", isApproved);

		LOGGER.info(
				"approveTimesheetData Request :: Completed for timesheetId::" + timesheetId + " :Action ::" + action);
		
		// send rejection mail to the employee
		if(isApproved == false) {
			
			try {
				
			MailContentUtil mailUtil = new MailContentUtil();
			mailUtil.sendRejectedNotification(entity, userRoleRepository, userRepository);
			
			}catch (Exception ex){
				ex.printStackTrace();
				LOGGER.info("Reject Mail :: FAILED" + timesheetId);
				return true;
			}
		}

		return true;
	}

	public Map<String, Object> getTaskDetails(String timesheetId) {
		LOGGER.info("getTaskDetails Request :: started for timesheetId::" + timesheetId);

		List<TimesheetTaskEntity> tsEntityList = timesheetTaskRepo.findByTimesheetId(timesheetId);

		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

		Iterator iter = tsEntityList.iterator();
		Map<String, Object> taskListDetail = new HashMap<String, Object>();
		List<Map<String, Object>> tasklist = new ArrayList<Map<String, Object>>();

		Optional<TimeSheetEntity> tsEntity = timesheetRepo.findById(timesheetId);

		if (tsEntity.isPresent()) {
			TimeSheetEntity entity = tsEntity.get();
			taskListDetail.put("timesheetId", entity.getId());
			taskListDetail.put("date", formatter.format(entity.getDate()));
			taskListDetail.put("calendar", entity.getCalendar());
			taskListDetail.put("userId", entity.getEmpId());
			taskListDetail.put("hours", String.valueOf(entity.getHours()));
			taskListDetail.put("minutes", String.valueOf(entity.getMinutes()));
			taskListDetail.put("usercomments", entity.getUserComments());
			taskListDetail.put("managercomments", entity.getManagerComments());
			taskListDetail.put("status", entity.getStatus());
			taskListDetail.put("sendNotificationsTo", entity.getNotifiedTo());
		}

		TimesheetTaskEntity taskEntity = null;
		while (iter.hasNext()) {
			taskEntity = (TimesheetTaskEntity) iter.next();
			Map<String, Object> taskDetail = new HashMap<String, Object>();
			taskDetail.put("taskId", taskEntity.getId());
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

		LOGGER.info("getTaskDetails Request :: Completed for timesheetId::" + timesheetId);

		return taskListDetail;
	}

	public ResponseEntity<?> approveSelectedOrders(List<TimeSheetEntity> timesheets, HttpServletRequest request) {

		SCAUtil scaU = new SCAUtil();

		try {

			if (timesheets == null || timesheets.size() == 0) {

				return new ResponseEntity(new ApiResponse(false, "Please select atleast one Timesheet to approve!"),
						HttpStatus.BAD_REQUEST);
			}

			TimesheetStatus status = TimesheetStatus.APPROVED;

			List<TimeSheetEntity> timeSheetList = new ArrayList<TimeSheetEntity>();

			for (TimeSheetEntity timeSheet : timesheets) {

				Optional<TimeSheetEntity> tsEntity = timesheetRepo.findById(timeSheet.getId());
				TimeSheetEntity entity = null;

				if (tsEntity.isPresent()) {

					entity = tsEntity.get();
					entity.setStatus(status.getCode());

					if (timeSheet.getManagerComments() != null) {

						entity.setManagerComments(timeSheet.getManagerComments());
					}

					entity.setUpdatedOn(LocalDateTime.now());
					entity.setApprovedOn(LocalDateTime.now());

					timeSheetList.add(entity);

				}
			}

			timesheetRepo.saveAll(timeSheetList);

		} catch (Exception e) {

			LOGGER.info("***************** Unable to approve Timesheets!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to approve Timesheets!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);
	}

	public ResponseEntity<?> getPendingTimesheetForApproval(HttpServletRequest http) {

		SCAUtil scaU = new SCAUtil();
		List<TimeSheetEntity> pendingTimeSheets = new ArrayList<TimeSheetEntity>();
		List<ManagerPendingApprovalTimesheet> managerApproval = new ArrayList<ManagerPendingApprovalTimesheet>();

		try {

			Long userId = scaU.getUserIdFromRequest(http, tokenProvider);

			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "Approval User is not found !"),
						HttpStatus.BAD_REQUEST);
			}

			user = opt.get();

			String manager_id = user.getEmployeeId();

			pendingTimeSheets = timesheetRepo.getPendingForApproval(manager_id);

			int addMinuteTime = 30;
			int addHoursTime = 5;

			for (TimeSheetEntity timeSheetEntity : pendingTimeSheets) {
				
				System.out.println(timeSheetEntity.getEmpId());
				
				User user1 = userRepository.findByEmployeeId(timeSheetEntity.getEmpId())
						.orElseThrow(() -> new AppException("User details does not exist."));
				ManagerPendingApprovalTimesheet newProfile = new ManagerPendingApprovalTimesheet();
				newProfile.setName(user1.getName());
				newProfile.setApprovedOn(timeSheetEntity.getApprovedOn());
				newProfile.setApprover(timeSheetEntity.getApprover());
				newProfile.setCreatedOn(timeSheetEntity.getCreatedOn());
//			       newProfile.setDate(timeSheetEntity.getDate());

				Date utcConvertDate = timeSheetEntity.getDate();

				utcConvertDate = DateUtils.addHours(utcConvertDate, addHoursTime);
				utcConvertDate = DateUtils.addMinutes(utcConvertDate, addMinuteTime);

				newProfile.setDate(utcConvertDate);

				newProfile.setEmpId(timeSheetEntity.getEmpId());
				newProfile.setHours(timeSheetEntity.getHours());
				newProfile.setId(timeSheetEntity.getId());
				newProfile.setManagerComments(timeSheetEntity.getManagerComments());
				newProfile.setMinutes(timeSheetEntity.getMinutes());
				newProfile.setNotifiedTo(timeSheetEntity.getNotifiedTo());
				newProfile.setStatus(timeSheetEntity.getStatus());
				newProfile.setSubmittedOn(timeSheetEntity.getSubmittedOn());
				newProfile.setUpdatedOn(timeSheetEntity.getUpdatedOn());
				newProfile.setUserComments(timeSheetEntity.getUserComments());

				List<TimesheetTaskEntity> taskEntity = timesheetTaskRepo.findByTimesheetId(timeSheetEntity.getId());
				String projects = user1.getName();

				for (TimesheetTaskEntity taskEntitys : taskEntity) {

					projects = projects.concat(" ").concat(taskEntitys.getProject());

				}

				newProfile.setProjects(projects);

				managerApproval.add(newProfile);

			}

			if (pendingTimeSheets == null) {

				pendingTimeSheets = new ArrayList<TimeSheetEntity>();
			}

		} catch (Exception e) {

			LOGGER.error("***************** Unable to get pending Timesheet list!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get pending Timesheet list!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(managerApproval, HttpStatus.OK);

	}

	public ResponseEntity<?> getSingleTimesheetById(String id) {
		if (!(timesheetRepo.existsById(id))) {

			return new ResponseEntity(new ApiResponse(false, "Timesheet with the ID : " + id + " does not exist"),
					HttpStatus.BAD_REQUEST);

		}

		List<TimesheetTaskEntity> taskEntity = timesheetTaskRepo.findByTimesheetId(id);

		return ResponseEntity.status(HttpStatus.OK).body(taskEntity);
	}

	@SuppressWarnings("deprecation")
	public void generateSampleFile(Workbook workbook, Sheet sheet1) throws Exception, AppException, ExcelException {

		Sheet sheet = sheet1;

		Double sum = 0.0;

		XSSFFont Header_Type_Font = (XSSFFont) workbook.createFont();

		Header_Type_Font.setBold(true);
		Header_Type_Font.setColor(IndexedColors.BLACK.index);

		XSSFCellStyle HeaderCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		HeaderCellStyle.setFont(Header_Type_Font);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle cellDateStyle = workbook.createCellStyle();
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

		Row row;
		Cell cell;

		row = sheet.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("Status");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("COMPLETED|IN PROCESS|CLOSED");
		cell.setCellStyle(HeaderCellStyle);

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue("S.No");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("Date (YYYY-MM-DD)");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Calendar");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(3);
		cell.setCellValue("Category");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(4);
		cell.setCellValue("Project");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(5);
		cell.setCellValue("Activity Description");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(6);
		cell.setCellValue("STATUS");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(7);
		cell.setCellValue("Time spent(mins)");
		cell.setCellStyle(HeaderCellStyle);

		cell = row.createCell(8);
		cell.setCellValue("Remarks");
		cell.setCellStyle(HeaderCellStyle);

		row = sheet.createRow(2);

		cell = row.createCell(0);
		cell.setCellValue("1");

//			cell = row.createCell(1);
//			cell.setCellValue(new Date("2022/02/10"));
//			cell.setCellStyle(cellDateStyle);

		cell = row.createCell(1);
		cell.setCellValue("2020-08-16");
		
		cell = row.createCell(2);
		cell.setCellValue("FocusR");


		cell = row.createCell(3);
		cell.setCellValue("Development");

		cell = row.createCell(4);
		cell.setCellValue("Internal");

		cell = row.createCell(5);
		cell.setCellValue("Meeting");

		cell = row.createCell(6);
		cell.setCellValue("Completed");

		cell = row.createCell(7);
		cell.setCellValue(180);

		cell = row.createCell(8);
		cell.setCellValue("status discussion");

		row = sheet.createRow(3);

		cell = row.createCell(0);
		cell.setCellValue("2");

//			cell = row.createCell(1);
//			cell.setCellValue(new Date("2022/02/19"));
//			cell.setCellStyle(cellDateStyle);

		cell = row.createCell(1);
		cell.setCellValue("2017-09-12");
		
		cell = row.createCell(2);
		cell.setCellValue("FocusR");

		cell = row.createCell(3);
		cell.setCellValue("Development");

		cell = row.createCell(4);
		cell.setCellValue("Internal");

		cell = row.createCell(5);
		cell.setCellValue("Meeting");

		cell = row.createCell(6);
		cell.setCellValue("Completed");

		cell = row.createCell(7);
		cell.setCellValue(180);

		cell = row.createCell(8);
		cell.setCellValue("status -discussion");

		row = sheet.createRow(4);

		cell = row.createCell(0);
		cell.setCellValue("3");

//			cell = row.createCell(1);
//			cell.setCellValue(new Date("2022/02/19"));
//			cell.setCellStyle(cellDateStyle);

		cell = row.createCell(1);
		cell.setCellValue("2017-09-12");
		
		cell = row.createCell(2);
		cell.setCellValue("FocusR");

		cell = row.createCell(3);
		cell.setCellValue("Development");

		cell = row.createCell(4);
		cell.setCellValue("Internal");

		cell = row.createCell(5);
		cell.setCellValue("Meeting");

		cell = row.createCell(6);
		cell.setCellValue("Completed");

		cell = row.createCell(7);
		cell.setCellValue(180);

		cell = row.createCell(8);
		cell.setCellValue("status -discussion");

		for (int i = 0; i < 30; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public ResponseEntity<?> readExcelData(File file) throws EncryptedDocumentException, IOException {

		Workbook workbook = WorkbookFactory.create(file);
		workbook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);

		List<TimeSheetUpload> taskEntityList = new ArrayList<TimeSheetUpload>();

		Map<String, TimeSheetUpload> temp_map = new HashMap<String, TimeSheetUpload>();

		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd");

		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				if (row.getRowNum() > 1) {
					DataFormatter formatter = new DataFormatter(); // creating formatter using the default locale
					DateFormatter dateFormatter = new DateFormatter();

					if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
						break; 

					}

					String rowNos = formatter.formatCellValue(row.getCell(0));
					Long rowNo = Long.parseLong(rowNos);
					

					Map<String, Object> requestData = new HashMap<String, Object>();
					TimeSheetUpload tsTaskDetail = new TimeSheetUpload();

					// tsTaskDetail.setId(formatter.formatCellValue(row.getCell(0)));
					String date = formatter.formatCellValue(row.getCell(1));
					String calendar = formatter.formatCellValue(row.getCell(2));
					String category = formatter.formatCellValue(row.getCell(3));
					String project = formatter.formatCellValue(row.getCell(4));
					String activity = formatter.formatCellValue(row.getCell(5));
					String status = formatter.formatCellValue(row.getCell(6));
					String minutes = formatter.formatCellValue(row.getCell(7));
					
					if(date.isEmpty()) {
						throw new BadRequestException(
								"Date is empty at row no " +rowNo);
					}
					
					if(category.isEmpty()) {
						throw new BadRequestException(
								"Category is empty at row no " + rowNo);
					}
					if(project.isEmpty()) {
						throw new BadRequestException(
								"Project is empty at row no " + rowNo);
					}
					if(activity.isEmpty()) {
						throw new BadRequestException(
								"Activity is empty at row no " +rowNo);
					}
					if(status.isEmpty()) {
						throw new BadRequestException(
								"Status is empty at row no " +rowNo);
					}
					if(minutes.isEmpty()) {
						throw new BadRequestException(
								"Minutes is empty at row no " +rowNo);
					}
					
					if(calendar.isEmpty()) {
						throw new BadRequestException(
								"Calendar is empty at row no " +rowNo);
					}
					
					if (date.contains("/")) {
						date = date.replace('/', '-');
					}
					LocalDate localDate = LocalDate.parse(date);
					tsTaskDetail.setTimesheetDate(localDate);
					tsTaskDetail.setCalendar(calendar);
					tsTaskDetail.setCategory(category);
					tsTaskDetail.setProject(project);
					tsTaskDetail.setActivity(activity);
					tsTaskDetail.setStatus(status);
					tsTaskDetail.setMinutes(Double.parseDouble(minutes));
					tsTaskDetail.setRemarks(formatter.formatCellValue(row.getCell(8)));
					
					taskEntityList.add(tsTaskDetail);

				}
				  
			}
			
		}

		// group date and sum the minutes
		Map<LocalDate, Double> Minutes = taskEntityList.stream().collect(Collectors
				.groupingBy(TimeSheetUpload::getTimesheetDate, Collectors.summingDouble(TimeSheetUpload::getMinutes)));

		// grouping the object based on date
		Map<LocalDate, List<TimeSheetUpload>> dateGroupTaskList = taskEntityList.stream()
				.collect(Collectors.groupingBy(TimeSheetUpload::getTimesheetDate));
		
		
		ResponseEntity<?> response = saveTimesheetBulkUploadData(dateGroupTaskList, Minutes);
		
		return response;

	}

	@SuppressWarnings({ "unused", "unchecked" })
	private ResponseEntity<?> saveTimesheetBulkUploadData(Map<LocalDate, List<TimeSheetUpload>> dateGroupTaskList,
			Map<LocalDate, Double> Minutes) {

		LOGGER.info("saveTimesheetData Request :: Started ");
		LOGGER.info("saveTimesheetData Request :: Started "+dateGroupTaskList);
		LOGGER.info("saveTimesheetData Request :: Started "+Minutes);
		

		// Fetching UserName from security config
		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		// Employee id fetch
		User user = userRepository.findByTimesheetUsername(name);
		String id = user.getEmployeeId();
		
		
		// validate the laptop submission

		if (user.getIs_laptop_details_submitted() == null || "N".equals(user.getIs_laptop_details_submitted())) {

			return new ResponseEntity(new ApiResponse(false, "Please Login again and Fill your Laptop details"), HttpStatus.BAD_REQUEST);
		}

		// method called to check for hours validation in Excel
		ProcessExcelDateforValidation(id, Minutes);

		for (Map.Entry<LocalDate, List<TimeSheetUpload>> entry : dateGroupTaskList.entrySet()) {
			LOGGER.info("saveTimesheetData Request :: Started :save database for loop started");

			String uploadDate = entry.getKey().toString();
			DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
			LocalDate tsLocalDate = LocalDate.parse(uploadDate, format);
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date tsDate = null;
			try {
				tsDate = formatter.parse(uploadDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Optional<TimeSheetEntity> tsEntity = timesheetRepo.findByIdandDate(id, tsDate);
			LOGGER.info("saveTimesheetData Request :: Started :Existing record fetching");

			if (tsEntity.isPresent() == false) {
				String timesheetId = initateTimeSheetProcess(id, entry.getKey().toString());
				tsEntity = timesheetRepo.findById(timesheetId);
			}

			TimeSheetEntity entity = null;

			if (tsEntity.get().getStatus().equals("SUBMITTED")) {
				throw new BadRequestException(
						"Timesheet for given date " + tsEntity.get().getDate() + " is already submitted");

			}
			

			if (tsEntity.get().getStatus().equals("APPROVED")) {
				throw new BadRequestException(
						"Timesheet for given date " + tsEntity.get().getDate() + " is already Approved");

			}

			if (tsEntity.isPresent() && !(tsEntity.get().getStatus().equals("SUBMITTED"))) {
				Double savedDbHours = 0.0;
				Double savedDbMinutes = 0.0;
				savedDbHours = tsEntity.get().getHours();
				savedDbMinutes = tsEntity.get().getMinutes();
				Double minutes = setHour(entry.getKey(), Minutes);
				Double hours = minutes / 60.0;
				Double totalHours = savedDbHours + hours;
				Double totalMinutes = savedDbMinutes + minutes;
				entity = tsEntity.get();
				entity.setHours(totalHours);
				entity.setMinutes(totalMinutes);
				entity.setStatus("SUBMITTED");
				entity.setApprover(userService.getManager(id));
				
				//entity.setCalendar();
				// entity.setUpdatedOn(LocalDateTime.now());
				entity.setSubmittedOn(LocalDateTime.now());
				// entity.setDate(date);

				List<TimeSheetUpload> tasklist = entry.getValue();
				int size = entry.getValue().size();
				
				List<String> newCalendarList = new ArrayList<String>();
				for (int i = 0; i < size; i++) {
					newCalendarList.add(tasklist.get(i).getCalendar());
				}
				
				Set<String> calendar = new HashSet<String>(newCalendarList);
				String calString = String.join(",", calendar);
				
				entity.setCalendar(calString);
				
				for (int i = 0; i < size; i++) {
					TimesheetTaskEntity tstaskEntity = new TimesheetTaskEntity();
					String timesheetTaskId = tasklist.get(i).getId();
					tstaskEntity.setId(timesheetTaskId);
					tstaskEntity.setTimesheetId(entity);
					tstaskEntity.setCategory((String) tasklist.get(i).getCategory());
					tstaskEntity.setProject((String) tasklist.get(i).getProject());
					tstaskEntity.setActivity((String) tasklist.get(i).getActivity());
					tstaskEntity.setPhase((String) tasklist.get(i).getPhase());
					tstaskEntity.setStatus((String) tasklist.get(i).getStatus().toUpperCase());
					tstaskEntity.setMinutes((tasklist.get(i).getMinutes()));
					tstaskEntity.setRemarks((String) tasklist.get(i).getRemarks());
					timesheetTaskRepo.save(tstaskEntity);
				}
				timesheetRepo.save(entity);
			} else {
				LOGGER.info("Timesheet ID not availabe in DB ");

			}
		}
		return new ResponseEntity(new ApiResponse(true, "File uploaded successfully"), HttpStatus.OK);
	}

	private void ProcessExcelDateforValidation(String id, Map<LocalDate, Double> Minutes) {
		LOGGER.info("saveTimesheetData Request :: Started :ProcessExcelDateforValidation");
		for (Map.Entry<LocalDate, Double> entry : Minutes.entrySet()) {
			Double uploadMinutes = 0.0;
			Double uploadHrs = 0.0;
			String uploadDate = entry.getKey().toString();
			DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
			LocalDate tsLocalDate = LocalDate.parse(uploadDate, format);
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date tsDate = null;
			try {
				tsDate = formatter.parse(uploadDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			uploadMinutes = entry.getValue();
			uploadHrs = uploadMinutes / 60;
			

			Optional<TimeSheetEntity> tsEntity = timesheetRepo.findByIdandDate(id, tsDate);	
			if (!tsEntity.isPresent()) {
				
				/*if (uploadHrs < 8.0) {
					throw new BadRequestException(
							"Time for given date " + tsDate.toString() + " is less than 8 hours");
				}*/  if(uploadHrs > 24.0) {
					throw new BadRequestException(
							"Time for given date " + tsDate.toString() + " is greater than 24 hours");
			 }
			} else if (tsEntity.isPresent() && !(tsEntity.get().getStatus().equals("SUBMITTED"))) {
				Double savedDbHours = 0.0;
				Double savedDbMinutes = 0.0;
				savedDbHours = tsEntity.get().getHours();
				savedDbMinutes = tsEntity.get().getMinutes();
				Double totalHours = savedDbHours + uploadHrs;
				Double totalMinutes = savedDbMinutes + uploadMinutes;
				/*if (totalHours < 8.0) {
					throw new BadRequestException("Time for given date and already saved date "
							+ tsEntity.get().getDate().toString() + " is less than 8 hours");

				}*/
				if (totalHours > 24.0) {
					throw new BadRequestException("Time for given date and already saved date "
							+ tsEntity.get().getDate().toString() + " is greater than 24 hours");

				}

			}

		}
		LOGGER.info("saveTimesheetData Request :: Started :ProcessExcelDateforValidation END");
	}

	private String initateTimeSheetProcess(String id, String date) {

		LOGGER.info("initiateProcess Request :: Started for userid:" + id + " with Date :" + date);

		String manager_id = userService.getManager(id);
		
		TimeSheetEntity tsEntity = new TimeSheetEntity();
		tsEntity.setEmpId(id);

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

		tsEntity.setCreatedOn(LocalDateTime.now());
		tsEntity.setDate(tsDate);
		tsEntity.setStatus("DRAFT");
		String managerId = userService.getManager(id);
		if(managerId.equals(id)) {
			throw new BadRequestException("Same User Cannot be a manager for himself");

		} else {
			tsEntity.setApprover(managerId);
		}
		
		tsEntity = timesheetRepo.save(tsEntity);
		String TimesheetId = tsEntity.getId();
		return TimesheetId;

	}

	private Double setHour(LocalDate key, Map<LocalDate, Double> Minutes) {
		Double minutes = 0.0;
		for (Map.Entry<LocalDate, Double> entry : Minutes.entrySet()) {
			LOGGER.info(entry.getKey() + "/" + entry.getValue());
			if ((key).equals(entry.getKey())) {
				minutes = entry.getValue();

			}
		}

		return minutes;

	}

}
