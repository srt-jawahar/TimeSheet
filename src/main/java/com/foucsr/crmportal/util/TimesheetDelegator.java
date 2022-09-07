package com.foucsr.crmportal.util;

import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.mysql.database.service.timesheet.ReportService;
import com.foucsr.crmportal.mysql.database.service.timesheet.TimesheetService;
import com.foucsr.crmportal.security.JwtTokenProvider;



@Service
public class TimesheetDelegator {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimesheetDelegator.class);

	@Autowired
	private ExcelUtil excelUtil;

	/*@Autowired
	private MailUtil mailUtil;*/

	@Autowired
	private JwtTokenProvider jwtToken;

	@Autowired
	private TimesheetService timesheetService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;


	public Response Login(Map<String, Object> requestData) {
		boolean authFlag = false;
		String userId = (String) requestData.get("userId");
		userId=userId.trim();
		String password = (String) requestData.get("password");
//		authFlag = identityService.checkPassword(userId, password);
		LOGGER.info("Login Request ::" + userId + "::" + authFlag);
		String role = "";
		String token = "";
		String name = "";
		String manager = "";
		String managerId = "";
		// Group groupDetails = null;
		if (authFlag) {

			// groupDetails =
			// identityService.createGroupQuery().groupMember(userId).singleResult();

			role = userService.getRole(userId);
			name = userService.getUserName(userId);
			managerId = userService.getManager(userId);
			manager = userService.getUserName(managerId);
			// groupId = groupDetails.getId();
			LOGGER.info("Logged in User :" + userId + "  Name : " + name + " Role : " + role);

//			token = jwtToken.generateJWTToken(userId, role);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
		}

		Response res = new Response();
		res.getResponseData().put("authFlag", authFlag);
		res.getResponseData().put("role", role);
		res.getResponseData().put("name", name);
		res.getResponseData().put("userId", userId);
		res.getResponseData().put("manager", manager);
		res.getResponseData().put("managerId", managerId);

		res.getResponseData().put("jwtToken", token);

		return res;
		// TODO Auto-generated method stub

	}

	public Response changePassword(Map<String, Object> requestData) {
		boolean authFlag = false;
		String oldPassword = (String) requestData.get("oldPassword");
		String newPassword = (String) requestData.get("newPassword");
		String userId = (String) requestData.get("userId");
//		authFlag = identityService.checkPassword(userId, oldPassword);
		String status = "";
		boolean reqFlag = false;

		if (authFlag) {

			LOGGER.info("change Password for User ::" + userId);
//			User user = identityService.createUserQuery().userId(userId).singleResult();
//			user.setPassword(newPassword);
//
//			identityService.saveUser(user);

			status = "Password Successfully changed";
			reqFlag = true;

		} else {
			status = "Unable to authenticate using old Password";
			reqFlag = false;
		}

		Response res = new Response();

		res.getResponseData().put("description", status);
		res.getResponseData().put("flag", reqFlag);

		return res;
		// TODO Auto-generated method stub

	}
	
	
	public Response resetPassword(Map<String, Object> requestData) {
		boolean authFlag = false;
		String managerPassword = (String) requestData.get("managerPassword");
		String newPassword = (String) requestData.get("teamMemberPassword");
		String userId = (String) requestData.get("userId");
		String teamMemberId = (String) requestData.get("teamMemberId");
//		authFlag = identityService.checkPassword(userId, managerPassword);
		String status = "";
		boolean reqFlag = false;

		if (authFlag) {

			LOGGER.info("reset Password for User ::" + teamMemberId);
			Map<String, Object> emp =userService.getEmp();
			
			Map<String, String> usrDetails = (Map<String, String>) emp.get(teamMemberId);
			
			if(usrDetails.get("managerId").equalsIgnoreCase(userId)){
				/*User user = identityService.createUserQuery().userId(teamMemberId).singleResult();
				user.setPassword(newPassword);

				identityService.saveUser(user);
*/
				status = "Password reset completed Successfully";
				reqFlag = true;
			}else {
				status = "Team member " +teamMemberId + " not assosiated to manager "+ userId ;
				reqFlag = true;
			}

		} else {
			status = "Unable to authenticate using old Password";
			reqFlag = false;
		}

		Response res = new Response();

		res.getResponseData().put("description", status);
		res.getResponseData().put("flag", reqFlag);

		return res;
		// TODO Auto-generated method stub

	}


	public Response initateTimeSheet(Map<String, Object> requestData) {

		String userId = (String) requestData.get("userId");
		String date = (String) requestData.get("date");

		LOGGER.info("Initiate Timesheet Request for UserId ::" + userId + ":: Date " + date);
		Map<String, Object> taskdetails = new HashMap<String, Object>();
		if (date != null && date.startsWith("20")) {

			String timesheetId = "";
			timesheetId = timesheetService.getTimesheetId(userId, date);

			if (timesheetId == "") {
				taskdetails = timesheetService.initiateProcess(userId, date);
				
				String validation = (String) taskdetails.get("validation");
				
				if(validation != null && "error".equals(validation)) {
					
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Employee");
					
				} else if(validation != null && "laptop_error".equals(validation)) {
					
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Login again and Fill your Laptop details");
				}
				taskdetails.put("isTimesheetExist", false);
			} else {
				taskdetails = timesheetService.getTaskDetails(timesheetId);
				taskdetails.put("isTimesheetExist", true);

			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Date");
		}

		Response res = new Response();
		res.setResponseData(taskdetails);

		return res;

	}

	public Response submitTimeSheet(Map<String, Object> requestData) {
		
		

		timesheetService.saveTimesheetData(requestData);
		// TODO Auto-generated method stub
		return null;
	}

	/*public Response getApprovalList(Map<String, Object> requestData) {

		String userId = (String) requestData.get("userId");

		Map<String, Object> tasklist = timesheetService.getTaskList(userId);

		Response res = new Response();
		res.setResponseData(tasklist);
		return res;
		// TODO Auto-generated method stub

	}*/

	public Response getTimesheetTaskDetails(Map<String, Object> requestData) {

		String timesheetId = (String) requestData.get("timesheetId");
//		String taskId = (String) requestData.get("taskId");

		Map<String, Object> tasklist = timesheetService.getTaskDetails(timesheetId);

//		tasklist.put("taskId", taskId);

		Response res = new Response();
		res.setResponseData(tasklist);
		return res;
		// TODO Auto-generated method stub

	}

	/*public Response approveTimesheet(Map<String, Object> requestData) {
		timesheetService.approveTimesheetData(requestData);

		Response res = new Response();
		return res;

	}*/

	public Response searchTimesheet(Map<String, Object> requestData) {

		/*
		 * String empId = String.valueOf(requestData.get("userId")); boolean
		 * noncomplainceflag =
		 * Boolean.valueOf(String.valueOf(requestData.get("userId"))); String
		 * month = String.valueOf(requestData.get("userId"));
		 */

		return null;
		// TODO Auto-generated method stub

	}

	public Response processExcelFile(Map<String, Object> requestData) {
		// TODO Auto-generated method stub.

		String filePath = (String) requestData.get("filepath");
		String fileName = (String) requestData.get("fileName");

		String message = "";
		if (filePath.equalsIgnoreCase("self")) {

			try {
				URL r = this.getClass().getResource("/");

				filePath = URLDecoder.decode(r.getFile(), "UTF-8");

			} catch (Exception e) {
				e.printStackTrace();

				message = e.getMessage() + "::::" + e.getLocalizedMessage();
			}

		}
		String excelmessage = excelUtil.processExcelFile(filePath + fileName);

		Response res = new Response();
		res.getResponseData().put("filePath", filePath + fileName);
		res.getResponseData().put("excelmessage", excelmessage);
		res.getResponseData().put("message", message);
		return res;

	}

	/*public Response sendMail(Map<String, Object> requestData) {
		// TODO Auto-generated method stub

		return mailUtil.sendMail(requestData);
	}*/

/*	public Response sendFeedback(Map<String, Object> requestData) {

		return referenceService.sendFeedback(requestData);
	}*/

	/*public Response getManagersList(Map<String, Object> requestData) {

		Response res = new Response();
		res.getResponseData().put("managerDetails", userService.getManagersList());
		return res;
	}*/

	/*public Response getReferenceData(Map<String, Object> requestData) {
		// TODO Auto-generated method stub

		Response res = new Response();
		res.getResponseData().put("managerList", userService.getManagersList());
		res.getResponseData().put("category", referenceService.getRefDataList("Category"));
		res.getResponseData().put("project", referenceService.getRefDataList("Project"));
		res.getResponseData().put("holidayList", referenceService.getHolidayList());
		res.getResponseData().put("employee_ref", userService.getEmp());
		return res;
	}*/

	public XSSFWorkbook getLeaveDetails(Map<String, Object> requestData) {
		String year = (String) requestData.get("year");
		String month = (String) requestData.get("month");

		YearMonth yearMonth = YearMonth.parse(year + "-" + month);
		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 2015-12-31
		return reportService.getLeaveData(startDate, endDate);
	}

	public XSSFWorkbook getTimsheetByEmpID(Map<String, Object> requestData) {
		String year = (String) requestData.get("year");
		String month = (String) requestData.get("month");
		String empid = (String) requestData.get("empid");
		boolean isDetailed = (boolean) requestData.get("isDetailed");
		YearMonth yearMonth = YearMonth.parse(year + "-" + month);
		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 2015-12-31
		return reportService.getTimsheetReport(empid, startDate, endDate, isDetailed);
	}

	public XSSFWorkbook getAggragatedTimeSheet(Map<String, Object> requestData) {
		String year = (String) requestData.get("year");
		String month = (String) requestData.get("month");

		YearMonth yearMonth = YearMonth.parse(year + "-" + month);
		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		return reportService.getLeaveData(startDate, endDate);
	}

	public Response getemployeedetails() {

		Response res = new Response();
		res.getResponseData().put("emp", userService.getEmp().values());

		return res;
	}

	public Response getReports(String role) {
		// TODO Auto-generated method stub
		
		Response res = new Response();
		res.getResponseData().put("reports", reportService.getReports(role));
		return res;
	}

	
	public Response approveTimesheet(Map<String, Object> requestData) {
		timesheetService.approveTimesheetData(requestData);

		Response res = new Response();
		return res;

	}
	

}
