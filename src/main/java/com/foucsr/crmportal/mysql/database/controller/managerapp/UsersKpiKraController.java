package com.foucsr.crmportal.mysql.database.controller.managerapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.managerapp.UsersKpiAndKra;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.UsersKpiAndKraRepository;
import com.foucsr.crmportal.mysql.database.service.managerapp.UsersKpiAndKraService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.SCAUtil;

@RestController
@RequestMapping("/api/UsersKpiAndKra/Service")
public class UsersKpiKraController {
	
	@Autowired
	private UsersKpiAndKraRepository usersKpiAndKraRepository;
	
	@Autowired
	private UsersKpiAndKraService usersKpiAndKraService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	SCAUtil scaUtil;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Create")
	public ResponseEntity<?> createKpiKra(@Valid @RequestBody UsersKpiAndKra kpiAndKra,HttpServletRequest httpServletRequest,
			BindingResult result, Principal principal) {

	try {
		
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 kpiAndKra.setUserId(userIdFromRequest);
		
		usersKpiAndKraService.createUsersKraKpi(kpiAndKra);
 
		
		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, "Unable to create Kpi and Kra Details!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra Details has been Created successfully "), HttpStatus.CREATED);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Update/{month}/{year}")
	public ResponseEntity<?> updateKpiKra(@Valid @RequestBody UsersKpiAndKra kpiAndKra,@PathVariable String month, @PathVariable String year,
			BindingResult result, Principal principal) {

	
		

		if(kpiAndKra.getId()==0) {
			return new ResponseEntity(new ApiResponse(false, "Please specify KPI/KRA Id to Update."),HttpStatus.BAD_REQUEST);
		}
		if(!(usersKpiAndKraRepository.existsById(kpiAndKra.getId()))) {
    		return new ResponseEntity(new ApiResponse(false,"KPI/KRA details With this Id does not exist"),HttpStatus.BAD_REQUEST);  
    	}	
		
		
		  return usersKpiAndKraService.updateUsersKraKpi(kpiAndKra,month,year);
 	
	
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/ApproveKpi")
	public ResponseEntity<?> approveKpiKra(@Valid @RequestBody UsersKpiAndKra kpiAndKra,BindingResult result, Principal principal) {

	
		

		if(kpiAndKra.getId()==0) {
			return new ResponseEntity(new ApiResponse(false, "Please specify KPI/KRA Id to Update."),HttpStatus.BAD_REQUEST);
		}
		if(!(usersKpiAndKraRepository.existsById(kpiAndKra.getId()))) {
    		return new ResponseEntity(new ApiResponse(false,"KPI/KRA details With this Id does not exist"),HttpStatus.BAD_REQUEST);  
    	}	
		
		
		  return usersKpiAndKraService.approveUsersKraKpi(kpiAndKra);
 	
	
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
 	@GetMapping("/getSingleUsersKpi/{id}")
      public ResponseEntity<?> getListOfKpisInAGroup(@PathVariable long id, Principal principal)throws AppException {
 		
 			
 			if(!(usersKpiAndKraRepository.existsById(id))) {
 				
 				return new ResponseEntity(new ApiResponse(false, "Users Kpi details with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

 			}
 			
 			UsersKpiAndKra newGrp =  usersKpiAndKraRepository.findById(id).orElseThrow(() -> new AppException("Kpi and Kra group does not exist."));
         	
 			
 			
 		return  ResponseEntity.status(HttpStatus.OK).body(newGrp);   
      }
	 

		@GetMapping("/getListOfUsersKpiAndKra")
		public List<UsersKpiAndKra> getAllProjects(Principal principal) {
			List<UsersKpiAndKra> listofKpis = usersKpiAndKraRepository.findAll();

//			List<KpiAndKraGroup> listofCandidates = kpiAndKraGroupRepository.findListOfKpiGroups();
			return listofKpis;
		}
		
		@GetMapping("/getUsersKpiKraForaMonth/{month}/{year}")
		public ResponseEntity<?> getUsersKpiKraForaMonth(HttpServletRequest httpServletRequest,@PathVariable String month, @PathVariable String year, Principal principal){
			
			 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
			 ResponseEntity<?> res = usersKpiAndKraService.getUsersKpiKraForaMonth(userIdFromRequest,month,year);
			return res;
		}
		
		@GetMapping("/getUsersKpiKraForaMonthForManager")
		public ResponseEntity<?> getUsersKpiKraForaMonthForManager(HttpServletRequest httpServletRequest, Principal principal){
			
			 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
			 ResponseEntity<?> res = usersKpiAndKraService.getPendingKpiApproval(userIdFromRequest);
			return res;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@GetMapping("/KpiReportManager/{month}/{year}")
		public ResponseEntity<?> kpiKraMonthlyReport(@PathVariable String month, @PathVariable String year, Principal principal ){

			 
			 String SHEET = "KPIAndKRA_Report";

				ByteArrayOutputStream out = new ByteArrayOutputStream();

				Workbook workbook = new XSSFWorkbook();

				InputStreamResource file = null;
				String filename = "KPIAndKRA_Report";
				String extension = ".xlsx";
				
				filename = filename  + extension; 

				try {
					
					

						Sheet sheet = workbook.createSheet(SHEET);

						usersKpiAndKraService.generateReport(workbook,sheet,month,year);
					//	candidateService.generateSampleFile(workbook,sheet);
						
						workbook.write(out);

						file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));


				} catch(Exception ex) {
					
					String msg = ex.getMessage() != null ? ex.getMessage() : "";
		
					String cause = "";
		
					if (ex.getCause() != null) {
		
						cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";
		
						msg = msg + "!!!" + cause;
					}
					return new ResponseEntity(new ApiResponse(false, " Unable to genrate report " + msg),
							HttpStatus.BAD_REQUEST);
		
				}

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+month+"_"+year+"_KpiAndKra_report.xlsx")
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@GetMapping("/KpiReportForSingleUser/{month}/{year}/{empId}")
		public ResponseEntity<?> kpiSingleUserReport(@PathVariable String month, @PathVariable String year, @PathVariable String empId,Principal principal ){

			User newUser = userRepository.findByEmployeeId(empId).orElseThrow(() -> new AppException("User details does not exist."));
			 String SHEET = "KPIAndKRA_Report";

				ByteArrayOutputStream out = new ByteArrayOutputStream();

				Workbook workbook = new XSSFWorkbook();

				InputStreamResource file = null;
				String filename = "KPIAndKRA_Report";
				String extension = ".xlsx";
				
				filename = filename  + extension; 

				try {
					
					

						Sheet sheet = workbook.createSheet(SHEET);

						usersKpiAndKraService.generateReportForUser(workbook,sheet,month,year,newUser.getId());
					//	candidateService.generateSampleFile(workbook,sheet);
						
						workbook.write(out);

						file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
						
						
					


				} catch(Exception ex) {
					
					String msg = ex.getMessage() != null ? ex.getMessage() : "";
		
					String cause = "";
		
					if (ex.getCause() != null) {
		
						cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";
		
						msg = msg + "!!!" + cause;
					}
					return new ResponseEntity(new ApiResponse(false, " Unable to genrate report " + msg),
							HttpStatus.BAD_REQUEST);
		
				}

				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+newUser.getEmployeeId()+"_"+month+"_"+year+"_KpiAndKra_report.xlsx")
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

		}
     

}
