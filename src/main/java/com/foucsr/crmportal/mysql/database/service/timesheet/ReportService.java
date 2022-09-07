package com.foucsr.crmportal.mysql.database.service.timesheet;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.repository.timesheet.TimeSheetRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.TimeSheetTaskRepository;
import com.foucsr.crmportal.util.ExcelUtil;
import com.foucsr.crmportal.util.ReportParameter;
import com.foucsr.crmportal.util.Reports;


@Service
public class ReportService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

	private String feedbackMailId;

	@Autowired
	private ExcelUtil excelUtil;

	@Autowired
	private TimeSheetRepository timesheetRepo;

	@Autowired
	private TimeSheetTaskRepository timesheetTaskRepo;

	public XSSFWorkbook getLeaveData(LocalDate startDate, LocalDate endDate) {
		List<TimesheetTaskEntity> tsEntityList = timesheetTaskRepo.findBycategory("Leave", LocalDateToDate(startDate),
				LocalDateToDate(endDate));

		XSSFWorkbook workbook = excelUtil.cretaeDetailedReport(tsEntityList);

		return workbook;

		// return null;
	}

	public XSSFWorkbook getTimsheetReport(String empid, LocalDate startDate, LocalDate endDate, boolean isDetailed) {
		XSSFWorkbook workbook = null;

		if (isDetailed) {
			List<TimesheetTaskEntity> tsEntityList = null;
			if (empid != null && "ALL".equals(empid)) {
				tsEntityList = timesheetTaskRepo.findByDate(LocalDateToDate(startDate), LocalDateToDate(endDate));
			} else {
				tsEntityList = timesheetTaskRepo.findByEmpId(empid, LocalDateToDate(startDate),
						LocalDateToDate(endDate));
			}
			workbook = excelUtil.cretaeDetailedReport(tsEntityList);

		} else {
			List<TimeSheetEntity> tsEntityList = null;
			if (empid != null && "ALL".equals(empid)) {
				tsEntityList = timesheetRepo.getMonthData(LocalDateToDate(startDate), LocalDateToDate(endDate));
			} else {
				tsEntityList = timesheetRepo.getMonthDataByEmp(empid, LocalDateToDate(startDate),
						LocalDateToDate(endDate));
			}
			workbook = excelUtil.cretaeTimesheetReport(tsEntityList);
		}

		return workbook;
	}
	
	@Cacheable(cacheNames="reports")
	public List<Reports> getReports(String role) {

		List<Reports> reportList = new ArrayList<Reports>();
		if(role.equals("Manager")){
		Reports emp_report = new Reports();
		
		
		emp_report.setReportId("REP_0001");
		emp_report.setReportName("Employee Details");
		emp_report.setReportDescription("Employee Details with Manager, Role and Designation");
		emp_report.setUrl("/timesheet/report/getemployeedetails");
		emp_report.setParam(null);
		
		reportList.add(emp_report);
		
		
		Reports leave_report = new Reports();
		
		
		leave_report.setReportId("REP_0002");
		leave_report.setReportName("Leave Report");
		leave_report.setReportDescription("Leave report for the given month for all employes");
		leave_report.setUrl("timesheet/report/leave/{Month/Year}");

		
		ReportParameter leaveRep_Param = new ReportParameter();
		List<ReportParameter> leaveRep_Param_lst = new ArrayList<ReportParameter>();
		
		leaveRep_Param.setName("Month/Year");
		leaveRep_Param.setOrder("1");
		leaveRep_Param.setType("Date");
		
		leaveRep_Param_lst.add(leaveRep_Param);
		
		leave_report.setParam(leaveRep_Param_lst);
		
		reportList.add(leave_report);
		}
		Reports timesheet_report = new Reports();
		
		
		timesheet_report.setReportId("REP_0003");
		timesheet_report.setReportName("Timesheet Report");
		timesheet_report.setReportDescription("Timesheet report for the given month");
		timesheet_report.setUrl("timesheet/report/timesheet/{empid}/{Month/Year}");

		
		ReportParameter timesheetRep_Param_1 = new ReportParameter();
		ReportParameter timesheetRep_Param_2 = new ReportParameter();
		List<ReportParameter> timesheetRep_Param_lst = new ArrayList<ReportParameter>();
		
		timesheetRep_Param_1.setName("empid");
		timesheetRep_Param_1.setOrder("1");
		timesheetRep_Param_1.setType("DropDown");
		
		timesheetRep_Param_1.setDropdownValue(null);
		
		timesheetRep_Param_lst.add(timesheetRep_Param_1);
		
		timesheetRep_Param_2.setName("Month/Year");
		timesheetRep_Param_2.setOrder("2");
		timesheetRep_Param_2.setType("Date");
		
		timesheetRep_Param_lst.add(timesheetRep_Param_2);
		
		timesheet_report.setParam(timesheetRep_Param_lst);
		
		reportList.add(timesheet_report);
		
		Reports timesheetDetails_report = new Reports();
		ReportParameter timesheetDetailRep_Param_1 = new ReportParameter();
		ReportParameter timesheetDetailRep_Param_2 = new ReportParameter();
		List<ReportParameter> timesheetDetailRep_Param_lst = new ArrayList<ReportParameter>();
		
		
		timesheetDetails_report.setReportId("REP_0004");
		timesheetDetails_report.setReportName("Detailed Timesheet Report");
		timesheetDetails_report.setReportDescription("Detailed Timesheet report for the given month");
		timesheetDetails_report.setUrl("timesheet/report/detailedtimesheet/{empid}/{Month/Year}");

		
		
		timesheetDetailRep_Param_1.setName("empid");
		timesheetDetailRep_Param_1.setOrder("1");
		timesheetDetailRep_Param_1.setType("DropDown");
		
		
		
		timesheetDetailRep_Param_1.setDropdownValue(null);
		
		timesheetDetailRep_Param_lst.add(timesheetDetailRep_Param_1);
		
		timesheetDetailRep_Param_2.setName("Month/Year");
		timesheetDetailRep_Param_2.setOrder("2");
		timesheetDetailRep_Param_2.setType("Date");
		
		timesheetDetailRep_Param_lst.add(timesheetDetailRep_Param_2);
		
		timesheet_report.setParam(timesheetDetailRep_Param_lst);
		
		reportList.add(timesheet_report);
		return reportList;
	}

	public Object getAggragatedTimeSheet(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date LocalDateToDate(LocalDate date) {

		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
	}

}
