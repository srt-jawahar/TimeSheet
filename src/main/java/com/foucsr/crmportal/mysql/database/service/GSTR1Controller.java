package com.focusr.chola.gstr1portal.mysql.database.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.focusr.chola.gstr1portal.mysql.database.service.GSTR1Services;
import com.focusr.chola.gstr1portal.mysql.database.service.GSTR2Services;
import com.focusr.chola.gstr1portal.mysql.database.service.byteMapValidationErrorService;
import com.focusr.chola.gstr1portal.payload.GSTR1Request;

@RestController
@RequestMapping("/api/GSTR1/Service")
public class GSTR1Controller {

	@Autowired
	GSTR1Services gSTR1Services;

	@Autowired
	private GSTR2Services gstr2Services;

	@Autowired
	private byteMapValidationErrorService bytemapvalidationerrorservice;

	@PostMapping("/uploadExcel")
	public String uploadExcel(@RequestParam("file") MultipartFile file) {
		try {
			byte[] excelData = file.getBytes();
			String excelName = file.getOriginalFilename();

			gSTR1Services.saveExcelData(excelData, excelName);

			return "Excel file uploaded successfully!";
		} catch (Exception e) {
			return "Error uploading Excel file: " + e.getMessage();
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getB2BCsvFile")
	public ResponseEntity<?> getB2BCsvUploadFile(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

//		ResponseEntity<?> message = gSTR1Services.getB2BCsvFile(gstr1Request);
//		ResponseEntity<?> message = gSTR1Services.getB2BCsvFileOwn(gstr1Request);

		ResponseEntity<?> message = gstr2Services.getB2BCsvFile(gstr1Request);

		return message;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getB2CCsvFile")
	public ResponseEntity<?> getB2CCsvFile(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

//		ResponseEntity<?> message = gSTR1Services.getB2BCsvFile(gstr1Request);
//		ResponseEntity<?> message = gSTR1Services.getB2CCsvFileOwn(gstr1Request);

		ResponseEntity<?> message = gstr2Services.getB2CCsvFile(gstr1Request);

		return message;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getHSNCsvFile")
	public ResponseEntity<?> getHSNCsvFile(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

//		ResponseEntity<?> message = gSTR1Services.getB2BCsvFile(gstr1Request);
//		ResponseEntity<?> message = gSTR1Services.getHSNCsvFile(gstr1Request);
//		ResponseEntity<?> message = gSTR1Services.getHSNCsvFilenw(gstr1Request);

		ResponseEntity<?> message = gstr2Services.getHSNCsvFile(gstr1Request);

		return message;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getInvoiceRegister")
	public ResponseEntity<?> getInvoiceRegister(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

		ResponseEntity<?> message = gstr2Services.getInvoiceRegister(gstr1Request);

		return message;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getRevanue")
	public ResponseEntity<?> getRevanue(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

		ResponseEntity<?> message = gstr2Services.getRevanue(gstr1Request);

		return message;

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/getStatewiseTrialBalence")
	public ResponseEntity<?> getStatewiseTrialBalence(HttpServletResponse response, @RequestBody GSTR1Request gstr1Request,
			BindingResult result, Principal principal) {

		ResponseEntity<byte[]> errorMap = bytemapvalidationerrorservice.MapValidationService(result);
		if (errorMap != null) {
			return new ResponseEntity(errorMap.getBody(), errorMap.getStatusCode());
		}

		ResponseEntity<?> message = gstr2Services.getStatewiseTrialBalence(gstr1Request);

		return message;

	}

}
