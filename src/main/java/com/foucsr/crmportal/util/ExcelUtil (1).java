package com.foucsr.ticketmanager.util;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import com.foucsr.ticketmanager.mysql.database.model.DailyReviewReport;
import com.foucsr.ticketmanager.payload.ApiResponse;


public class ExcelUtil {
	
	Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	//public ResponseEntity<?> getDailyReviewExcelReport(List<DailyReviewReport>  dailyreviewreport) 
	public ResponseEntity<?> getDailyReviewExcelReport(List<DailyReviewReport>  dailyreviewreport)
	{

		String SHEET = "Daily_Review_Report";

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Workbook workbook = new XSSFWorkbook();

		InputStreamResource file = null;
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "Daily_Review_Report_" + currentDateTime;
		String extension = ".xlsx";
		
		filename = filename  + extension; 

		try {

				Sheet sheet = workbook.createSheet(SHEET);

				createDailyReportTitleRow(sheet, workbook);

				createDailyReportValues(sheet, workbook, dailyreviewreport);

				workbook.write(out);

				file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));


		} catch (IOException e) {

			return new ResponseEntity(new ApiResponse(false, "fail to import Daily Review report to Excel file: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

	}
	
	private List<String> getDailyReportTitleLables() {

		List<String> list = new ArrayList<String>();
		list.add("S.No");
		list.add("Ticket No");
		list.add("Requester Name");
		list.add("Group Name");
		list.add("Subject");
		list.add("Status");
		list.add("Project Name");
		list.add("Created Date");
		list.add("Resolution Date");
		list.add("Closed Date");
		
	//	list.add("Agent");
			
		return list;
	}


	private void createDailyReportTitleRow(Sheet sheet, Workbook workbook) {

		// title row
		Row titleRow = sheet.createRow(0);

		List<String> labelList = getDailyReportTitleLables();

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

			Cell valueCell = valueRow.createCell(columnCount);
			
		
			valueCell = valueRow.createCell(++columnCount);
			String ticket_no = report.getTICKET_NO() == null ? "" : report.getTICKET_NO();
			valueCell.setCellValue(ticket_no);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, true , shortVal );

			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getFROM_ADDRESS());
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getGROUP_NAME());
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			valueCell = valueRow.createCell(++columnCount);
			String subject = report.getSUBJECT();
			if (subject.contains("#")) {
				String subSubject =  subject.split("#")[0];
			valueCell.setCellValue(subSubject);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			}
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getSTATUS());
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getPROJECT_NAME());
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal );
			
			String date_str = getFormattedMonthCaps(report.getcreatedAt());
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(date_str);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			String date_st = getFormattedMonthCaps(report.getupdatedAt());
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(date_st);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);
			
			String date_ct = getFormattedMonthCaps(report.getCLOSED_DATE());
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(date_ct);
			setStyleForDailyReport(workbook, valueCell, HorizontalAlignment.CENTER, false , shortVal);

		}

	}
	
	private String getFormattedMonthCaps(Date date) {

		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
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
	


}
