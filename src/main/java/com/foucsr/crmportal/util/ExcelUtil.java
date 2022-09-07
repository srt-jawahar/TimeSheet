package com.foucsr.crmportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.RoleRepository;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;



/**
 * @author Muthuganesh
 * 
 */
@Component
public class ExcelUtil {


	/*@Autowired
	private MailUtil mailUtil;*/

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRoleRepository userRoleRepo;

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);


	public String processExcelFile(String excelFilePath) {
		FileInputStream inputStream;
		String message = "";
		try {
			inputStream = new FileInputStream(new File(excelFilePath));

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			Map<String, Object> requestData;

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				String empName = nextRow.getCell(0).getStringCellValue();
				String empId = nextRow.getCell(1).getStringCellValue();
				String emailId = nextRow.getCell(2).getStringCellValue();
				String designation = nextRow.getCell(3).getStringCellValue();
				String manager = nextRow.getCell(5).getStringCellValue();
				String role = nextRow.getCell(7).getStringCellValue();

				User user = new User();

				user.setEmployeeId(empId);
				user.setName(empName);
				user.setEmail(emailId);

				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setEmpId(empId);
				userRole.setDesignation(designation);
				userRole.setManagerId(manager);
				userRole.setRole(role);
				userRepo.save(user);
				userRoleRepo.save(userRole);
				String key = RandomStringUtils.randomAlphabetic(5);
				String password = empId + "_" + key;
//				authUtil.createUser(empId, empName, "", emailId, password);

				requestData = new HashMap<String, Object>();
				String mailTo = emailId;

				String mailSubject = " User " + empName + " Registered in Timesheet Application";
				
				String app_url = AppConstants.App_Url;

				String content = "Dear " + empName + ",\n " + " Your Employee ID : " + empId
						+ " has been registed in the timesheet application. Please find the URL and credential below"
						+ "\n TimeSheet Application URL : " + app_url + "\n" + "\n User ID : " + empId + "\n"
						+ " Password : " + password + "\n" + "\n\n\n"
						+ "Please send mail to timesheet@focusrtech.com is case of any issues" + "\n\n"
						+ "Thanks \n Timesheet Application team";
				requestData.put("to", mailTo);
				requestData.put("mailSubject", mailSubject);
				requestData.put("mailContent", content);
				requestData.put("attachment", false);

//				mailUtil.sendMail(requestData);
				message = "success";
				System.out.println();
			}

			workbook.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
			e.printStackTrace();
		}

		return message;
	}

	public XSSFWorkbook cretaeDetailedReport(List<TimesheetTaskEntity> tsEntityList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Leave Report");
		Row row = sheet.createRow(0);
		int columnCount = 0;
		row.createCell(columnCount++).setCellValue("EMP_ID");
		row.createCell(columnCount++).setCellValue("EMP_Name");
		row.createCell(columnCount++).setCellValue("Date");
		row.createCell(columnCount++).setCellValue("Status");
		row.createCell(columnCount++).setCellValue("Category");
		row.createCell(columnCount++).setCellValue("Project");
		row.createCell(columnCount++).setCellValue("Activity");
		row.createCell(columnCount++).setCellValue("Minutes");
		row.createCell(columnCount++).setCellValue("Hour");
		Map<String, Object> empData = userService.getEmp();
		int rowCount = 1;
		Iterator iter = tsEntityList.iterator();
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		TimesheetTaskEntity task = null;
		while (iter.hasNext()) {

			try {
				task = (TimesheetTaskEntity) iter.next();
				createDetailedTaskRow(task, sheet, rowCount++, empData, cellStyle);
			} catch (Exception e) {
				LOGGER.info("Exception in cretaeDetailedReport for task" + task.getTimesheetId() + " with exception :"
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return workbook;

	}

	private void createDetailedTaskRow(TimesheetTaskEntity task, XSSFSheet sheet, int rowNo,
			Map<String, Object> empData, CellStyle cellStyle) {
		// TODO Auto-generated method stub
		Row row = sheet.createRow(rowNo);
		Map<String, String> empDetails = (Map<String, String>) empData.get(task.getTimesheetId().getEmpId());
		int columnCount = 0;
		row.createCell(columnCount++).setCellValue(task.getTimesheetId().getEmpId());
		row.createCell(columnCount++).setCellValue(empDetails.get("name"));
		row.createCell(columnCount++).setCellValue(task.getTimesheetId().getDate());
		row.getCell(columnCount - 1).setCellStyle(cellStyle);
		row.createCell(columnCount++).setCellValue(task.getTimesheetId().getStatus());
		row.createCell(columnCount++).setCellValue(task.getCategory());
		row.createCell(columnCount++).setCellValue(task.getProject());
		row.createCell(columnCount++).setCellValue(task.getActivity());
		row.createCell(columnCount++).setCellValue(task.getMinutes());
		row.createCell(columnCount++).setCellValue(task.getMinutes() / 60);

	}

	private void createTimeSheetRow(TimeSheetEntity task, XSSFSheet sheet, int rowNo, Map<String, Object> empData,
			CellStyle cellStyle) {
		// TODO Auto-generated method stub
		Row row = sheet.createRow(rowNo);
		
		Map<String, String> empDetails = (Map<String, String>) empData.get(task.getEmpId());
		int columnCount = 0;
		String status = task.getStatus();
		
		row.createCell(columnCount++).setCellValue(task.getEmpId());
		row.createCell(columnCount++).setCellValue(empDetails.get("name"));
		row.createCell(columnCount++).setCellValue(task.getDate());
		row.getCell(columnCount - 1).setCellStyle(cellStyle);
		row.createCell(columnCount++).setCellValue(task.getCalendar());
		if (!status.equals("DRAFT")) {
			row.createCell(columnCount++).setCellValue(task.getSubmittedOn().toString());
		} else {
			row.createCell(columnCount++);
		}
		row.createCell(columnCount++).setCellValue(task.getStatus());
		if (status.equals("APPROVED")) {
			row.createCell(columnCount++).setCellValue(task.getApprovedOn().toString());
		} else {
			row.createCell(columnCount++);
		}
		row.createCell(columnCount++).setCellValue(task.getHours());
		row.createCell(columnCount++).setCellValue(task.getMinutes());
		row.createCell(columnCount++).setCellValue(empDetails.get("managerId"));
		row.createCell(columnCount++).setCellValue(empDetails.get("managerName"));

	}

	public XSSFWorkbook cretaeTimesheetReport(List<TimeSheetEntity> tsEntityList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("TimeSheet Report");
		Row row = sheet.createRow(0);
		int columnCount = 0;
		row.createCell(columnCount++).setCellValue("Emp_Id");
		row.createCell(columnCount++).setCellValue("Emp_Name");
		row.createCell(columnCount++).setCellValue("Date");
		row.createCell(columnCount++).setCellValue("Calendar");
		row.createCell(columnCount++).setCellValue("Submitted_ON");
		row.createCell(columnCount++).setCellValue("Status");
		row.createCell(columnCount++).setCellValue("Approved_On");
		row.createCell(columnCount++).setCellValue("Hours");
		row.createCell(columnCount++).setCellValue("Minutes");
		row.createCell(columnCount++).setCellValue("Manager_Id");
		row.createCell(columnCount++).setCellValue("Manager_Name");
		int rowCount = 1;
		Iterator iter = tsEntityList.iterator();
		Map<String, Object> empData = userService.getEmp();
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
		TimeSheetEntity task = null;
		while (iter.hasNext()) {
			try {
				task = (TimeSheetEntity) iter.next();
				createTimeSheetRow(task, sheet, rowCount++, empData, cellStyle);
			} catch (Exception e) {
				LOGGER.info("Exception in cretaeTimesheetReport for task" + task.getId() + " with exception :"
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return workbook;
	}

}