package com.focusr.chola.allocationportal.util.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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

import com.focusr.chola.allocationportal.payload.ApiResponse;
import com.focusr.chola.allocationportal.respclass.JVResp;

public class JVExcelUtil {

	Logger logger = LoggerFactory.getLogger(JVExcelUtil.class);

	public ResponseEntity<?> getJVReport(List<JVResp> list,String period, Integer year) {

		String SHEET = "15.JV_Report_"+period+(year%100);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Workbook workbook = new XSSFWorkbook();

		InputStreamResource file = null;
		String filename = "15.JV_Report_"+period+(year%100);
		String extension = ".xlsx";

		filename = filename + extension;

		try {

			Sheet sheet = workbook.createSheet(SHEET);

			createPendingReportTitleRow(sheet, workbook);

			String nullValueStr = createPendingReportValues(sheet, workbook, list);
			
			if (nullValueStr != null && nullValueStr.contains("Null")) {

				return new ResponseEntity(new ApiResponse(false, nullValueStr), HttpStatus.BAD_REQUEST);
			}

			workbook.write(out);

			file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

		} catch (IOException e) {

			return new ResponseEntity(
					new ApiResponse(false, "fail to download " + filename + " report to Excel file: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

	}

	private void createPendingReportTitleRow(Sheet sheet, Workbook workbook) {

		// title row
		Row titleRow = sheet.createRow(0);

		List<String> labelList = getPendingReportTitleLables();

		int columnCount = 0;

		short shortVal = -1;

		CellStyle cellStyle = setValueCellColor(workbook, HorizontalAlignment.CENTER, true, shortVal);

		for (String label : labelList) {

			Cell titleCell = titleRow.createCell(columnCount++);
			titleCell.setCellValue(label);
			titleCell.setCellStyle(cellStyle);

		}

	}

	private CellStyle setValueCellColor(Workbook workbook, HorizontalAlignment align, boolean isBold, short s) {

		XSSFFont font = (XSSFFont) workbook.createFont();

		if (isBold) {

			font.setBold(true);
		}

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(align);

		if (s >= 0) {

			cellStyle.setFillForegroundColor(s);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;

	}

	private List<String> getPendingReportTitleLables() {

		List<String> list = new ArrayList<String>();
		list.add("Period");
		list.add("Year");
		list.add("Company");
		list.add("Product");
		list.add("Branch");
		list.add("CostCentre");
		list.add("Account");
		list.add("Future");
		list.add("Source");
		list.add("Debit");
		list.add("Credit");
		list.add("JVDescription");

		return list;
	}

	private String createPendingReportValues(Sheet sheet, Workbook workbook, List<JVResp> list) {

		int rowCount = 1;

		short shortVal = -1;
		CellStyle cellStyle = setValueCellColor(workbook, HorizontalAlignment.CENTER, false, shortVal);

		for (JVResp report : list) {

			int columnCount = 0;

			Row valueRow = sheet.createRow(rowCount++);

			Cell valueCell = null;
			
			try {
			valueCell = valueRow.createCell(columnCount);
			valueCell.setCellValue(report.getPERIOD());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Period. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getYEAR());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Year. Please check.";
			}

			
			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getCompany());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Company. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getProduct());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Product. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getBranch());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Branch. Please check.";
			}


			try {
			String str = report.getCostCentre();
			String cctwoDigit = String.format("%02d", Integer.parseInt(str));
			
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(cctwoDigit);
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in CC. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getAccount());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Account. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getFuture());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Future. Please check.";
			}

			
			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getSOURCE());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Source. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getDebit());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Debit. Please check.";
			}


			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getCredit());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in Credit. Please check.";
			}

			
			try {
			valueCell = valueRow.createCell(++columnCount);
			valueCell.setCellValue(report.getJVDescription());
			valueCell.setCellStyle(cellStyle);
			} catch (NullPointerException ex) {

				return "Null value present in JV-Description. Please check.";
			}


		}
		
		return "";

	}

}
