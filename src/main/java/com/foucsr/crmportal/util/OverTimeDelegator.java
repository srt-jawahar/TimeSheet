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
import com.foucsr.crmportal.mysql.database.service.timesheet.OvereTimeService;
import com.foucsr.crmportal.mysql.database.service.timesheet.ReportService;
import com.foucsr.crmportal.mysql.database.service.timesheet.TimesheetService;
import com.foucsr.crmportal.security.JwtTokenProvider;



@Service
public class OverTimeDelegator {

	private static final Logger LOGGER = LoggerFactory.getLogger(OverTimeDelegator.class);

	@Autowired
	private ExcelUtil excelUtil;

	/*@Autowired
	private MailUtil mailUtil;*/

	@Autowired
	private JwtTokenProvider jwtToken;

	@Autowired
	private OvereTimeService overtimeService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;


	public Response initateTimeSheet(Map<String, Object> requestData) {

		String userId = (String) requestData.get("userId");
		String date = (String) requestData.get("date");

		Map<String, Object> taskdetails = new HashMap<String, Object>();
		if (date != null && date.startsWith("20")) {

			String timesheetId = "";
			timesheetId = overtimeService.getOverTimeId(userId, date);

			if (timesheetId == "") {
				taskdetails = overtimeService.initiateProcess(userId, date);
				
				String validation = (String) taskdetails.get("validation");
				
				if(validation != null && "error".equals(validation)) {
					
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Employee");
				}
				taskdetails.put("isOverTimeExist", false);
			} else {
				taskdetails = overtimeService.getTaskDetails(timesheetId);
				taskdetails.put("isOverTimeExist", true);

			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Date");
		}

		Response res = new Response();
		res.setResponseData(taskdetails);

		return res;

	}

	public Response submitOverTime(Map<String, Object> requestData) {

		overtimeService.saveOverTimeData(requestData);
		// TODO Auto-generated method stub
		return null;
	}

	public Response getTimesheetTaskDetails(Map<String, Object> requestData) {

		String overtimeId = (String) requestData.get("overtimeId");
//		String taskId = (String) requestData.get("taskId");

		Map<String, Object> tasklist = overtimeService.getTaskDetails(overtimeId);

//		tasklist.put("taskId", taskId);

		Response res = new Response();
		res.setResponseData(tasklist);
		return res;
		// TODO Auto-generated method stub

	}
	
	public Response approveOverTime(Map<String, Object> requestData) {
		overtimeService.approveOverTimeData(requestData);

		Response res = new Response();
		return res;

	}


}
