package com.focusr.chola.gstr1portal.mysql.database.service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.focusr.chola.gstr1portal.mysql.database.model.GSTR1Entity;
import com.focusr.chola.gstr1portal.mysql.database.repository.GSTR1Repository;
import com.focusr.chola.gstr1portal.payload.ApiResponse;
import com.focusr.chola.gstr1portal.payload.GSTR1Request;
import com.focusr.chola.gstr1portal.respclass.sumOfB2CResp;
import com.focusr.chola.gstr1portal.respclass.sumOfHsnResp2;
import com.focusr.chola.gstr1portal.util.UtilClass;
import com.focusr.chola.gstr1portal.util.excel.B2BCsvRowUtility;
import com.focusr.chola.gstr1portal.util.excel.B2CCsvRowUtility;
import com.focusr.chola.gstr1portal.util.excel.GSTStateWiseUtility;
import com.focusr.chola.gstr1portal.util.excel.HSNCsvRowUtility;
import com.focusr.chola.gstr1portal.util.excel.InvoiceRegisterUtility;
import com.focusr.chola.gstr1portal.util.excel.RevanueUtility;
import com.opencsv.CSVWriter;

@Service
public class GSTR2Services {

	@Autowired
	private GSTR1Repository gstr1repository;

	Logger logger = LoggerFactory.getLogger(GSTR2Services.class);

	UtilClass util = new UtilClass();

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getB2BCsvFile(GSTR1Request gstr1Request) {
		try {
			List<GSTR1Entity> queryResult = gstr1repository.getB2BDetails(gstr1Request.getPeriodMonth(),
					gstr1Request.getPeriodYear());

			if (queryResult.isEmpty()) {

				return new ResponseEntity<>(new ApiResponse(false,
						"No Data Found for this " + gstr1Request.getPeriodMonth() + " " + gstr1Request.getPeriodYear()),
						HttpStatus.BAD_REQUEST);

			}

			return generateB2BCsvFile(queryResult, gstr1Request);

		} catch (Exception e) {

			logger.error("Unable to Generate B2B  File!\n" + e);

			String msg = util.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Generate B2B  File!" + msg),
					HttpStatus.BAD_REQUEST);

		}
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateB2BCsvFile(List<GSTR1Entity> queryResult, GSTR1Request gstr1Request) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

			CSVWriter csvWriter = new CSVWriter(writer);

			// Get Header
//			String[] headerRow = B2BCsvRowUtility.prepareB2BHeaderRow();
			String[] headerRow = B2BCsvRowUtility.prepareB2BHeaderRow();
			csvWriter.writeNext(headerRow);

			for (GSTR1Entity primary : queryResult) {

//				Get Insert Data
				String[] rowData = B2BCsvRowUtility.prepareCsvRow(primary, gstr1Request);
				csvWriter.writeNext(rowData);
			}

			csvWriter.flush();
			String csvData = outputStream.toString(StandardCharsets.UTF_8.name());

			HttpHeaders headers = B2BCsvRowUtility.prepareB2BResponseHeaders(gstr1Request);

			return new ResponseEntity<>(csvData, headers, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Unable to Insert Data In B2B File!\n" + e);

			String msg = util.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Insert Data In B2B File!" + msg),
					HttpStatus.BAD_REQUEST);
		}
	}

	// ************************************************//

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getB2CCsvFile(GSTR1Request gstr1Request) {

		List<Integer> toStateList = new ArrayList<>();

		try {

			List<String> getSupplierGST = gstr1repository.getB2CDistGstNo(gstr1Request.getPeriodMonth(),
					gstr1Request.getPeriodYear());

			if (getSupplierGST.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "No Supplier GST found for this "
						+ gstr1Request.getPeriodMonth() + " " + gstr1Request.getPeriodYear()), HttpStatus.BAD_REQUEST);
			}

			List<sumOfB2CResp> resultList = new ArrayList<>();

			for (String supplierGST : getSupplierGST) {

				toStateList = gstr1repository.getToState(supplierGST,gstr1Request.getPeriodMonth(),
						gstr1Request.getPeriodYear());

				for (Integer state : toStateList) {
					List<Double> taxRateList = gstr1repository.getTaxRate(state, supplierGST,gstr1Request.getPeriodMonth(),
							gstr1Request.getPeriodYear());

					for (Double rate : taxRateList) {

						List<sumOfB2CResp> totalSumList = gstr1repository.getB2CCalculations(supplierGST, state, rate,gstr1Request.getPeriodMonth(),
								gstr1Request.getPeriodYear());

						if (!totalSumList.equals(null) || !totalSumList.isEmpty()) {

							resultList.addAll(totalSumList);
						}
					}
				}
			}

			if (resultList.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "No data found"), HttpStatus.NOT_FOUND);
			}

			return generateB2CCsvFile(resultList, gstr1Request);

		} catch (Exception e) {

			logger.error("Unable to Generate B2C  File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity(new ApiResponse(false, "Unable to Generate B2C  File!" + msg),
					HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateB2CCsvFile(List<sumOfB2CResp> resultList, GSTR1Request gstr1Request) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
			CSVWriter csvWriter = new CSVWriter(writer);

			// Get Header
			String[] headerRow = B2CCsvRowUtility.prepareB2CHeaderRow();
			csvWriter.writeNext(headerRow);

			for (sumOfB2CResp primary : resultList) {

//			Get Insert Data

				if (primary != null) {
					String[] rowData = B2CCsvRowUtility.prepareB2CRow(primary, gstr1Request);
					csvWriter.writeNext(rowData);
				}
			}

			csvWriter.flush();
			String csvData = outputStream.toString(StandardCharsets.UTF_8.name());

			HttpHeaders headers = B2CCsvRowUtility.prepareB2CResponseHeaders(gstr1Request);

			return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
		}

		catch (Exception e) {

			logger.error("Unable to Insert Data In B2C File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity(new ApiResponse(false, "Unable to Insert Data In B2C File!" + msg),
					HttpStatus.BAD_REQUEST);
		}

	}

//**************************HSN*******************************//

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getHSNCsvFile(GSTR1Request gstRequest) {
		try {

			List<sumOfHsnResp2> resultList = new ArrayList<>();

			List<String> supplierGSTList = gstr1repository
					.getGSTRecordsByDistinctSupplierGSTId(gstRequest.getPeriodMonth(), gstRequest.getPeriodYear());

			for (String supplierGST : supplierGSTList) {

				List<Integer> invoiceHSNList = gstr1repository.getInvoiceHSNBySupplierGST(supplierGST,gstRequest.getPeriodMonth(), gstRequest.getPeriodYear());

				for (Integer invoiceHsN : invoiceHSNList) {
					List<Double> invoiceRate = gstr1repository.getInvoiceRate(invoiceHsN, supplierGST,gstRequest.getPeriodMonth(), gstRequest.getPeriodYear());


					for (Double rate : invoiceRate) {
						List<sumOfHsnResp2> invoiceSumResults = gstr1repository.getHSNCalculations(supplierGST,
								invoiceHsN, rate,gstRequest.getPeriodMonth(), gstRequest.getPeriodYear());

//						System.out.println("Invoice Sum Result" + invoiceSumResults);

						resultList.addAll(invoiceSumResults);
					}
				}
			}

			if (resultList.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "No data found"), HttpStatus.NOT_FOUND);
			}

			return generateHSNCsvFile(resultList, gstRequest);
		} catch (Exception e) {
			logger.error("Unable to Generate  HSN  File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity(new ApiResponse(false, "Unable to Generate  HSN  File!!" + msg),
					HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateHSNCsvFile(List<sumOfHsnResp2> resultList, GSTR1Request gstr1Request) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
			CSVWriter csvWriter = new CSVWriter(writer);

			// Get Header
			String[] headerRow = HSNCsvRowUtility.prepareHSNHeaderRow();
			csvWriter.writeNext(headerRow);

			for (sumOfHsnResp2 primary : resultList) {
//			Get Insert Data
				String[] rowData = HSNCsvRowUtility.prepareHSNRow(primary, gstr1Request);
				csvWriter.writeNext(rowData);
			}

			csvWriter.flush();

			String csvData = outputStream.toString(StandardCharsets.UTF_8.name());

			HttpHeaders headers = HSNCsvRowUtility.prepareHSNResponseHeaders(gstr1Request);

			return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
		}

		catch (Exception e) {

			logger.error("Unable to Insert Data In CSV File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity(new ApiResponse(false, "Unable to Insert Data In CSV File" + msg),
					HttpStatus.BAD_REQUEST);
		}

	}

	//////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getInvoiceRegister(GSTR1Request gstr1Request) {

		List<GSTR1Entity> queryResult = gstr1repository.getB2BDetails(gstr1Request.getPeriodMonth(),
				gstr1Request.getPeriodYear());

		return generateInvoiceRegister(queryResult, gstr1Request);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateInvoiceRegister(List<GSTR1Entity> queryResult, GSTR1Request gstr1Request) {
		HttpHeaders headers = null;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				XSSFWorkbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("Invoice Register");

			// Create header row
			String[] headerRow = InvoiceRegisterUtility.createHeaderRow(sheet);

			int rowNum = 2;

			for (GSTR1Entity primary : queryResult) {
				// Get Insert Data
				String[] rowData = InvoiceRegisterUtility.prepareInvoiceRegister(primary, gstr1Request);

				// Create and fill the row
				Row row = sheet.createRow(rowNum++);

				InvoiceRegisterUtility.fillRowWithData(row, rowData);
			}

			workbook.write(outputStream);

			headers = InvoiceRegisterUtility.prepareInvoiceRegisterResponseHeaders();

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		}

		catch (FileNotFoundException e) {

			logger.error("File not found while generating Invoice Register Excel File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity<>(new ApiResponse(false, "File not found: " + msg), HttpStatus.NOT_FOUND);
		}

		catch (IOException e) {

			logger.error("Unable to Insert Data Invoice Register Excel File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity<>(
					new ApiResponse(false, "Unable to Insert Data In Invoice Register Excel File" + msg),
					HttpStatus.BAD_REQUEST);

		}
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getRevanue(GSTR1Request gstRequest) {

		List<String> supplierGSTList = gstr1repository.getGSTRecordsByDistinctSupplierGSTId(gstRequest.getPeriodMonth(),
				gstRequest.getPeriodYear());

		return generateRevanue(supplierGSTList);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateRevanue(List<String> supplierGSTList) {

		HttpHeaders headers = null; // Declare headers outside the try block
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				XSSFWorkbook workbook = new XSSFWorkbook()) {

			try {

				Sheet sheet = workbook.createSheet("Summary");

				// Get Header
				String[] headerRow = RevanueUtility.prepareRevanueHeaderRow(sheet ,supplierGSTList);

				workbook.write(outputStream);

				headers = RevanueUtility.prepareRevanueResponseHeaders();
			} catch (IOException e) {
				// Handle IOException
			}

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Unable to Insert Data Invoice Register Excel File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity<>(
					new ApiResponse(false, "Unable to Insert Data  In Invoice Register Excel File" + msg),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getStatewiseTrialBalence(GSTR1Request gstRequest) {
		

//		List<String> supplierGSTList = gstr1repository.getGSTRecordsByDistinctSupplierGSTId(gstRequest.getPeriodMonth(),
//				gstRequest.getPeriodYear());

		return generateStatewiseTrialBalence();
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> generateStatewiseTrialBalence() {

		HttpHeaders headers = null; // Declare headers outside the try block
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				XSSFWorkbook workbook = new XSSFWorkbook()) {

			try {

				Sheet sheet = workbook.createSheet("GST_TRIAL_BALANCE");

				// Get Header
				String[] headerRow = GSTStateWiseUtility.CreateHeader(sheet );

				workbook.write(outputStream);

				headers = GSTStateWiseUtility.CreateResponseHeaders();
			} catch (IOException e) {
				// Handle IOException
			}

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Unable to Insert Data Invoice Register Excel File!\n" + e);
			String msg = util.getErrorMessage(e);
			return new ResponseEntity<>(
					new ApiResponse(false, "Unable to Insert Data  In Invoice Register Excel File" + msg),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	

}
