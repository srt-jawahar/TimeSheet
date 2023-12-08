package com.foucsr.ticketmanager.util;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.foucsr.ticketmanager.mysql.database.model.CustomComponent;
import com.foucsr.ticketmanager.mysql.database.model.ProjectCategory;
import com.foucsr.ticketmanager.mysql.database.model.ReceiveEmailData;
import com.foucsr.ticketmanager.mysql.database.repository.CustomRepository;
import com.foucsr.ticketmanager.mysql.database.repository.EmailDetailsRepository;
import com.foucsr.ticketmanager.mysql.database.repository.ProjectRepository;
import com.foucsr.ticketmanager.mysql.database.repository.ReceiveEmailDataRepository;
import com.foucsr.ticketmanager.payload.ApiResponse;
import com.foucsr.ticketmanager.payload.ReportRoot;

@Service
public class SchedulerExcelUtil {
	
	@Autowired
	private  CustomRepository customRepo;
	
	@Autowired
	public ProjectRepository projectRepo;
	
	@Autowired
	EmailDetailsRepository emailDetailsRepository;

	@Autowired
	ReceiveEmailDataRepository recevieEmaildataRepo;
	
	

	@Autowired
	EmailHtmlLoader emailHtmlLoader;
	
	Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public File getDailyReviewExcelReport(List<DailyReviewReport> dailyreviewreport,String projectName,
			ServletContext context) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException
	{
		
		String SHEET = "Daily_Review_Report";
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Workbook workbook = new XSSFWorkbook();

		InputStreamResource file = null;
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());
		String orgName = "Daily_Review_Report_" + currentDateTime;
		String extension = ".xlsx";
		
		orgName = orgName + extension; 
		
//		File attachment  = null;

		File attachFile = null;
		
		try {
			
		    
				Sheet sheet = workbook.createSheet(SHEET);

				createDailyReportTitleRow(sheet, workbook,projectName);

				createDailyReportValues(sheet, workbook, dailyreviewreport);

				workbook.write(out);
				

				file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

				    String uploadsDir = "/attachments/";

				    String realPathtoUploads = context.getRealPath(uploadsDir);


				    if (!new File(realPathtoUploads).exists()) {

				        new File(realPathtoUploads).mkdir();

				    }
//
//				    int startIndex = orgName.lastIndexOf(".");
//
//				    String fileFormatStr = orgName.substring(startIndex);
//
//				    orgName = orgName + fileFormatStr;

				    String filePath = realPathtoUploads + orgName;

				    attachFile = new File(filePath);
				
				OutputStream outputStream = new FileOutputStream(filePath);
		        out.writeTo(outputStream);
				
//		        mailGeneration(dailyreviewreport,attachFile);
					

		} catch (IOException e) {

//			return new ResponseEntity(new ApiResponse(false, "fail to import Daily Review report to Excel file: " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
		}

//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
		
		return attachFile;

	}
	
//	list.add("Project Name");
	private List<String> getDailyReportTitleLables(String projectName) {

		List<String> list = new ArrayList<String>();
		list.add("Ticket No");
		list.add("Requester Name");
		list.add("Group Name");
		list.add("Subject");
		list.add("Status");
		list.add("Created Date");
		list.add("Resolution Date");
		list.add("Closed Date");
		list.add("Due Date");
		list.add("First Response Time(HRS)");
		list.add("First Response Status");
		list.add("Respond Status");
		list.add("Resolution Time");
		list.add("Time Tracker");
		list.add("Ticket Priority");
		list.add("Ticket Age");

		
		// dynamically create Reports for Custom 
//		System.out.println( " project name :" +param.getProjectName());
//		String projectName = param.getProjectName(); 
		List<CustomValue> customComponents = customRepo.findByProjectLabels(projectName);
//		System.out.println( "customComponents :" + customComponents);
		for(CustomValue customFields : customComponents) {
//			System.out.println( " Getting custom field " + customFields.getLABEL());
		list.add(customFields.getLABEL());
		}
		
		return list;
	}

	private void createDailyReportTitleRow(Sheet sheet, Workbook workbook,String projectName) {

		// title row
		Row titleRow = sheet.createRow(0);

		List<String> labelList = getDailyReportTitleLables(projectName);

		int columnCount = 0;

		for (String label : labelList) {

			Cell titleCell = titleRow.createCell(columnCount++);
			titleCell.setCellValue(label);
			
			short shortVal = -1;
			
			setStyleForDailyReport(workbook, titleCell, HorizontalAlignment.CENTER, true, shortVal);
			
		}

	}
	
	
	private void createDailyReportValues(Sheet sheet, Workbook workbook, List<DailyReviewReport> list) {

		int rowCount = 1;

		for (DailyReviewReport report : list) {

			int columnCount = 0;

			Row valueRow = sheet.createRow(rowCount++); 
			
			short shortVal = -1;

			Cell valueCell = valueRow.createCell(columnCount--);
			
		    //TicketNo
			valueCell = valueRow.createCell(++columnCount);
			String ticket_no = report.getTICKET_NO() == null ? "" : report.getTICKET_NO();
			valueCell.setCellValue(ticket_no);
			System.out.println( " Ticket No : " +ticket_no);
			int columnIndex = valueCell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, true , shortVal );

			// From Address......
			valueCell = valueRow.createCell(++columnCount);
			String fromAddress = report.getFROM_ADDRESS();
			if(fromAddress.contains("<")) {
				String subFromAddress = fromAddress.split("<")[0];
				valueCell.setCellValue(subFromAddress);
				int columnIndex1 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex1);
				setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			}
			else if (fromAddress.contains("@")) {
				String subFromAddress = fromAddress.split("@")[0];
				valueCell.setCellValue(subFromAddress);
				int columnIndex1 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex1);
				setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			}
			
			// Group Name
			valueCell = valueRow.createCell(++columnCount);
			String groupName = report.getGROUP_NAME();
			if(groupName == null) {
				valueCell.setCellValue("--");
				int columnIndex2 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex2);
			} else {
				valueCell.setCellValue(report.getGROUP_NAME());
				int columnIndex2 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex2);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			// Subject....
			valueCell = valueRow.createCell(++columnCount);
			String subject = report.getSUBJECT();
			if (subject.contains("@")) {
			String subSubject = subject.split("@")[1];
			if (subSubject.contains("#")) {
			String subSubjects = subSubject.split("#")[0];
			valueCell.setCellValue(subSubjects);
			int columnIndex3 = valueCell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex3);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
				}}
			else if(subject.contains("#")) {
				String subSubject =  subject.split("#")[0];
				valueCell.setCellValue(subSubject);
				int columnIndex3 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex3);
				setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			}
			
			// Status...
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getSTATUS());
			int columnIndex4 = valueCell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex4);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
//			// Project...
//			valueCell = valueRow.createCell(++columnCount);
//			valueCell.setCellValue(report.getPROJECT_NAME());
//			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			// Ticket Creation.....
			String date_str = getFormattedMonthCaps(report.getClientCreationDate());
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(date_str);
			int columnIndex5 = valueCell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex5);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			// Ticket Based on client Status Update Date....
			String date_st = getFormattedMonthCaps(report.getRESOLUTION_DATE());
			valueCell = valueRow.createCell(++columnCount);
			if(date_st == null) {
				valueCell.setCellValue("--");
				int columnIndex6 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex6);
			} else {
				valueCell.setCellValue(date_st);
				int columnIndex6 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex6);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			// Ticket Close Date and time...
			String date_ct = getFormattedMonthCaps(report.getCLOSED_DATE());
			valueCell = valueRow.createCell(++columnCount);
			if(date_ct == null) {
				valueCell.setCellValue("--");
				int columnIndex7 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex7);
			} else {
				valueCell.setCellValue(date_ct);
				int columnIndex7 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex7);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			//Ticket Due Date and time.....
			String dueDate = getFormattedMonthCaps(report.getDUE_DATE());
			valueCell = valueRow.createCell(++columnCount);
			if(dueDate == null) {
				valueCell.setCellValue("--");
				int columnIndex8 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex8);
			} else {
				valueCell.setCellValue(dueDate);
				int columnIndex8 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex8);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			// First Response Time .....
			valueCell = valueRow.createCell(++columnCount);
			int response = report.getFirstResponseTime();
			if(response == 0) {
				valueCell.setCellValue("--");
				int columnIndex9 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex9);
			} else {
				Duration d = Duration.ofMinutes(response);
				LocalTime hackUseOfClockAsDuration = LocalTime.MIN.plus(d);
				String output = hackUseOfClockAsDuration.toString();
				System.out.println(" Response Value : " + output);

				String[] timeParts = output.split(":");

				int hours = Integer.parseInt(timeParts[0]);
				int minutes = Integer.parseInt(timeParts[1]);

				String responseTime = hours + "Hr" + ":" + minutes + "Min";
				System.out.println(" responseTime : " + responseTime);
				// int firstResponseTime = report.getFirstResponseTime();
				valueCell.setCellValue(responseTime);
				int columnIndex9 = valueCell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex9);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			// First Response Status ......
			valueCell = valueRow.createCell(++columnCount);
			String slaStatus = report.getSLA_RESPONSE_STATUS();
			if(slaStatus == null) {
				valueCell.setCellValue("--");
				int columnIndex10 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex10);
			} else {
				valueCell.setCellValue(report.getSLA_RESPONSE_STATUS());
				int columnIndex10 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex10);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			//Total Ticket SLA Status
			valueCell = valueRow.createCell(++columnCount);
			String closeStatus = report.getSLA_RESOLVE_STATUS();
			if(closeStatus == null) {
				valueCell.setCellValue("--");
				int columnIndex11 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex11);
			} else {
				valueCell.setCellValue(report.getSLA_RESOLVE_STATUS());
				int columnIndex11 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex11);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);

			// Resolved Time .....
			valueCell = valueRow.createCell(++columnCount);
			int resolvedTime = report.getResolutionTime();
			if (resolvedTime == 0) {
				valueCell.setCellValue("--");
				int columnIndex11 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex11);
			} else {
				Duration d = Duration.ofMinutes(resolvedTime);
				LocalTime hackUseOfClockAsDuration = LocalTime.MIN.plus(d);
				String output = hackUseOfClockAsDuration.toString();
				System.out.println(" Resolve Value : " + output);

				String[] timeParts = output.split(":");

				int hours = Integer.parseInt(timeParts[0]);
				int minutes = Integer.parseInt(timeParts[1]);

				String resolveTime = hours + "Hr" + ":" + minutes + "Min";
				System.out.println(" resolveTime : " + resolveTime);
				// int firstResponseTime = report.getFirstResponseTime();
				valueCell.setCellValue(resolveTime);
				int columnIndex9 = valueCell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex9);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			
			// Time Tracker .....
			valueCell = valueRow.createCell(++columnCount);
			int timeTracker = report.getTimeTracker();
			if (timeTracker == 0) {
				valueCell.setCellValue("--");
				int columnIndex12 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex12);
			} else {
				valueCell.setCellValue(report.getTimeTracker());
				int columnIndex12 = valueCell.getColumnIndex();
	            sheet.autoSizeColumn(columnIndex12);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			
			//Ticket Priority...
			valueCell = valueRow.createCell(++columnCount);
			String ticketpriority = report.getTICKET_PRIORITY();
			if (ticketpriority == null) {
				valueCell.setCellValue("--");
			} else {
				valueCell.setCellValue(ticketpriority);
				int columnIndex15 = valueCell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex15);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			
			// Ticket Age...
			valueCell = valueRow.createCell(++columnCount);
			String ticketAge = getTicketVarience(ticket_no);
			if (ticketAge == null) {
				valueCell.setCellValue("--");
			} else {
				valueCell.setCellValue(ticketAge);
				int columnIndex15 = valueCell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex15);
			}
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
			
			// Dynamically Updating the Values in Custom Fields....
			valueCell = valueRow.createCell(++columnCount);
			List<CustomComponent> customComponents = customRepo.findByTicketValues(ticket_no);
			for (CustomComponent customFields : customComponents) {
				String type = customFields.getType();
				if(type.equals("text")) {
				String titleValue = customFields.getValue();
				if (titleValue == null) {
					valueCell.setCellValue("--");
				} else {
					valueCell.setCellValue(customFields.getValue());
					int columnIndex16 = valueCell.getColumnIndex();
		            sheet.autoSizeColumn(columnIndex16);
				}
//				setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
				}
				if (type.equals("select")) {
					Long customId = customFields.getCustom_id();
					
				List<CustomFields> dropdownList = customRepo.dropDownOptions(customId);
				for (CustomFields customField : dropdownList) {
					
					boolean selected = customField.getSELECTED_VALUE();
					
					if (selected == true) {
						String titleValue = customField.getvalue();
						if (titleValue == null) {
							valueCell.setCellValue("--");
						} else {
							valueCell.setCellValue(customField.getvalue());
							int columnIndex22 = valueCell.getColumnIndex();
				            sheet.autoSizeColumn(columnIndex22);
						}
					}
					
				}
				}
				setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false, shortVal);
				valueCell = valueRow.createCell(++columnCount);
			}
			

		}

	}
	
	private String getFormattedMonthCaps(Date date) {

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
		String dateString = "";

		try {

			if (date != null) {

				dateString = formatter.format(date);

			} else {

				dateString = "--";
			}

		} catch (Exception e) {

			dateString = "//";
		}

		return dateString;
	}
	
	private void setStyleForDailyReport(Workbook workbook, Cell titleCell, HorizontalAlignment align, boolean isBold , short s) {

		XSSFFont font = (XSSFFont) workbook.createFont();

		if (isBold) {

			font.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(align);
		
		
        if(s >= 0) {
			
			cellStyle.setFillForegroundColor(s);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		cellStyle.setFont(font);
		titleCell.setCellStyle(cellStyle);
		

	}
	
	/// Mail Generation 
public ResponseEntity<?> mailGeneration(List<DailyReviewReport> dailyreviewreports,File file) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException{
		
		System.out.println("in side sendReplyMail");
		
		
		String emailFrom = new String();

		List<String> emailTo = new ArrayList<String>();
		List<String> emailCC = new ArrayList<String>();
		List<File> files = new ArrayList<File>();
		String subject = new String();

		EmailSubject emailSubject = null;
		String projectName = "ORACLE";
		ProjectCategory projectcategory = projectRepo.findByName(projectName);
		Long projectId = projectcategory.getProjectId();
		emailSubject = EmailSubject.getInstanceList(emailDetailsRepository, projectId); // Get the Email Creditional
		

		subject = " Daily eview report-(For daily status report)";
		
//		String ccString = receivedata.getEmailCC();
//		emailCC = new ArrayList<String>(Arrays.asList(ccString.split(";")));
		
//		String text = emailHtmlLoader.getMailText(receivedata);
		
		String text =  "Hi All, \r\n"
				+ "\r\n"
				+ "Please find the attached daily status report in this mail.\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "Thanks,\r\n"
				+ "FocusR Support";
		

		// Changed for understanding postman
		emailTo.add("saishivak11@gmail.com");

		//URL Path for 
		String url = "";

		System.out.println(" URL : " + url);

		//Form Address......
		String fromEmail = projectcategory.getEmailAddress();
		
		emailFrom = fromEmail;
		
		
		// File Attachment ..........
		
		List<File> listFile = new ArrayList<File>();
		
		if(file != null) {
			
			listFile.add(file);
		}
		
		files = listFile;
		
		System.out.println("File :" + files);
		

// Print Statement..........
		System.out.println("emailFrom :" + emailFrom);
		System.out.println("emailTo :" + emailTo);
		System.out.println("emailCC :" + emailCC);
		System.out.println("subject :" + subject);
		System.out.println("text :" + text);
		System.out.println("File :" + files);

		emailSubject.init(emailFrom, emailTo, emailCC,files,subject, text, url);
		emailSubject.setHTML(true);

		SendMail sendmail = new SendMail();

		sendmail.sendMail(emailSubject);
		
		return new ResponseEntity(new ApiResponse(true, "Mail sent successfully"), HttpStatus.OK);
	}

	// Ticket Age Varience ........
	public String getTicketVarience(String ticketNo) {

		ReceiveEmailData ticketDetails = null;
		SCAUtil sca = new SCAUtil();

		String value = null;
		try {

			ticketDetails = recevieEmaildataRepo.findByTicket(ticketNo);

			if (ticketDetails == null) {

				// return new ResponseEntity(new ApiResponse(false, "Unable to
				// get projects!"), HttpStatus.BAD_REQUEST);
			}

			Date CreatedDate = ticketDetails.getClientCreationDate();

			LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(CreatedDate));

			// Define the two dates
			LocalDate date1 = localDate;
			LocalDate date2 = LocalDate.now(); // October 16, 2023

			// Calculate the difference in days
			long daysDifference = ChronoUnit.DAYS.between(date1, date2);

			if (daysDifference >= 0) {

				value = daysDifference + "-" + "Days";
			} else {
				value = null;
			}

		} catch (Exception e) {

			logger.error("***************** Unable to get projects!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			// return new ResponseEntity(new ApiResponse(false, "Unable to get
			// projects!" + msg), HttpStatus.BAD_REQUEST);
		}

		return value;

	}

}
