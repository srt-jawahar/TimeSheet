package com.foucsr.crmportal.mysql.database.controller.timesheet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ExcelException;
import com.foucsr.crmportal.mysql.database.model.Designation;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.model.timesheet.Projects;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.repository.timesheet.ProjectsRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.TimeSheetTaskRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.timesheet.TimesheetService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.security.UserPrincipal;
import com.foucsr.crmportal.util.IdAndValuePair;
import com.foucsr.crmportal.util.Request;
import com.foucsr.crmportal.util.Response;
import com.foucsr.crmportal.util.TimesheetDelegator;



@RestController
@RequestMapping("/api/Timesheet/Service")
public class TimesheetRestController {

	public static final Logger log = LoggerFactory.getLogger(TimesheetRestController.class);

	@Autowired
	private TimesheetDelegator timesheetDelegator;
	
	@Autowired
	private TimeSheetTaskRepository timeSheetTaskRepository;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired
	private ProjectsRepository projectsRepository;


	@PostMapping(path = "/Login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response Login(@RequestBody Request request) {

		Response res = timesheetDelegator.Login(request.getRequestData());
		return res;
	}
	
	@GetMapping(path = "/Login/{userId}/{password}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response Loginget(@PathVariable String userId, @PathVariable String password) {

		Request req = new Request();
		
		req.getRequestData().put("userId", userId);
		req.getRequestData().put("password", password);
		Response res = timesheetDelegator.Login(req.getRequestData());
		return res;
	}

	@PostMapping(path = "/changePassword", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response changePassword(Authentication authentication, @RequestBody Request request) {

		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.changePassword(request.getRequestData());
		return res;
	}
	
	@PostMapping(path = "/resetPassword", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response resetPassword(Authentication authentication, @RequestBody Request request) {

		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.resetPassword(request.getRequestData());
		return res;
	}
	
	@CacheEvict(allEntries=true)
	@GetMapping(path = "/clearCache", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response clearCache() {

		return null;
	}

	@PostMapping(path = "/initiate", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response initateTimeSheet(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.initateTimeSheet(request.getRequestData());
		return res;
	}

	@PostMapping(path = "/submit", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response submitTimeSheet(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.submitTimeSheet(request.getRequestData());
		return res;
	}

	@GetMapping(path = "/isexist/{id}/{date}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response checkIfTimesheetExist(Authentication authentication,
			@RequestBody Request request) {

		boolean timesheetExist = false;
		// timesheetExist =
		// timesheetDelegator.checkIfTimesheetExist(request.getRequestData());

		Response res = new Response();
		res.getResponseData().put("timesheetExist", timesheetExist);
		return res;
	}

	/*@PostMapping(path = "/approvallist", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response getApprovalList(Authentication authentication,
			@RequestBody Request request) {
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.getApprovalList(request.getRequestData());
		return res;
	}*/

	@PostMapping(path = "/approvaltask", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response gettaskdetails(Authentication authentication, @RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.getTimesheetTaskDetails(request.getRequestData());
		return res;
	}

	/*@PostMapping(path = "/complete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response approveTimesheet(Authentication authentication,
			@RequestBody Request request) {
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.approveTimesheet(request.getRequestData());
		return res;
	}*/

	@PostMapping(path = "/search/{view}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response searchTimesheet(Authentication authentication, @RequestBody Request request,
			@PathVariable String view) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.searchTimesheet(request.getRequestData());
		return res;
	}

	@PostMapping(path = "/processfile", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response processExcelFile(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.processExcelFile(request.getRequestData());
		return res;
	}

	/*@PostMapping(path = "/sendmail", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response sendMail(Authentication authentication, @RequestBody Request request) {
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.sendMail(request.getRequestData());
		return res;
	}*/
	
	/*@PostMapping(path = "/feedback", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response sendFeedback(Authentication authentication, @RequestBody Request request) {
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.sendFeedback(request.getRequestData());
		return res;
	}*/
	
	/*@GetMapping(path = "/getmanagers", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response getManagersList(Authentication authentication) {
		
		Request request = new Request();
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.getManagersList(request.getRequestData());
		return res;
	}*/
	
	/*@GetMapping(path = "/getrefdata",  produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response getReferenceData(Authentication authentication) {
		
		Request request = new Request();
		request.getRequestData().put("userId",authentication.getName());
		Response res = timesheetDelegator.getReferenceData(request.getRequestData());
		return res;
	}*/
	
	@GetMapping(path = "/report/getemployeedetails",  produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response getEmployeeDetails(Authentication authentication) {

	
		String reqRole = authentication.getAuthorities().iterator().next().getAuthority();
		
		if("ROLE_MANAGER".equalsIgnoreCase(reqRole)
				|| "ROLE_ADMIN".equalsIgnoreCase(reqRole)) {
			Response res = timesheetDelegator.getemployeedetails();
			return res;
		} else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
		}
		
	}
	
	@GetMapping(path = "/report/leave/{month}/{year}")
	public HttpEntity<ByteArrayResource> getLeaveDetails(Authentication authentication, @PathVariable String month,@PathVariable String year) {
		
        String reqRole = authentication.getAuthorities().iterator().next().getAuthority();
		
		if("ROLE_MANAGER".equalsIgnoreCase(reqRole)
				|| "ROLE_ADMIN".equalsIgnoreCase(reqRole)) {
		Request request = new Request();
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		request.getRequestData().put("month",month);
		request.getRequestData().put("year",year);
		String fileName = "LeaveReport_"+month+"_"+year+".xlsx";
		
		
		try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = timesheetDelegator.getLeaveDetails(request.getRequestData()); // creates the workbook
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +fileName);
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		}else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
		}
	}
	
	@GetMapping(path = "/report/timesheet/{empid}/{month}/{year}")
	public HttpEntity<ByteArrayResource> getTimsheetForManager(Authentication authentication, @PathVariable String empid,@PathVariable String month,@PathVariable String year, boolean isDetailed) {
		
		
		Request request = new Request();
		
		
		request.getRequestData().put("month",month);
		request.getRequestData().put("year",year);
		request.getRequestData().put("empid",empid);
		request.getRequestData().put("isDetailed",isDetailed);
		String fileName = "TimeSheet_Report_"+month+"_"+year+".xlsx";
        String reqRole = authentication.getAuthorities().iterator().next().getAuthority();
		
		if("ROLE_MANAGER".equalsIgnoreCase(reqRole)
				|| "ROLE_ADMIN".equalsIgnoreCase(reqRole)) {
			UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//			request.getRequestData().put("userId",authentication.getName());
			request.getRequestData().put("userId",up.getEmployeeId());
		}else if (empid.equals(authentication.getName())){
			UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//			request.getRequestData().put("userId",authentication.getName());
			request.getRequestData().put("userId",up.getEmployeeId());
            
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserID No Valid");
		}
		
		try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = timesheetDelegator.getTimsheetByEmpID(request.getRequestData()); // creates the workbook
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +fileName);
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping(path = "/report/aggregate/{month}/{year}")
	public HttpEntity<ByteArrayResource> getAggragatedTimeSheet(Authentication authentication, @PathVariable String month,@PathVariable String year) {
		
		
		Request request = new Request();
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		request.getRequestData().put("month",month);
		request.getRequestData().put("year",year);
		String fileName = "LeaveReport_"+month+"_"+year+".xlsx";
        String reqRole = authentication.getAuthorities().iterator().next().getAuthority();
		
		if("ROLE_MANAGER".equalsIgnoreCase(reqRole)
				|| "ROLE_ADMIN".equalsIgnoreCase(reqRole)) {
		
		
		try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = timesheetDelegator.getAggragatedTimeSheet(request.getRequestData()); // creates the workbook
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +fileName);
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		} else {
			 throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
		}
		
	}
	
	@GetMapping(path = "/report/detailedtimesheet/{empid}/{month}/{year}")
	public HttpEntity<ByteArrayResource> getDetailedTimsheetByEmpID(Authentication authentication, @PathVariable String empid,@PathVariable String month,@PathVariable String year) {
		
		
		return getTimsheetForManager(authentication, empid, month, year, true) ;
			
	}
	
	@GetMapping(path = "/report/list")
	public @ResponseBody Response getReports(Authentication authentication) {

		
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		Response res = timesheetDelegator.getReports(role);
		return res;
	}
	
	@GetMapping("/getSingleTaskById/{id}")
	 public ResponseEntity<?> getSingleTask(@PathVariable String id, Principal principal)throws AppException {
		
		
		if(!(timeSheetTaskRepository.existsById(id))) {
			
			return new ResponseEntity(new ApiResponse(false, "Task with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

		}
		
		TimesheetTaskEntity timesheetTaskEntity = timeSheetTaskRepository.findById(id).orElseThrow(() -> new AppException("Task does not exist."));;
		
		
	return  ResponseEntity.status(HttpStatus.OK).body(timesheetTaskEntity);   
}	
	
	@GetMapping("/getListOfCategory")
public List<IdAndValuePair> getCategoryTypes() {
		
		List<IdAndValuePair> categoryIDandValue = new ArrayList<>();
		List<String> categoryTypes = new ArrayList<>();
		Long id = (long) 1;
		
		categoryTypes.add("Competency");
		categoryTypes.add("Knowledge Transfer");
		categoryTypes.add("Training");
		categoryTypes.add("Development");
		categoryTypes.add("Testing");
		categoryTypes.add("Sourcing");
		categoryTypes.add("Onboarding");
		categoryTypes.add("Payroll");
		categoryTypes.add("HR");
		categoryTypes.add("Accounts");
		categoryTypes.add("Review");
		categoryTypes.add("Sales");
		categoryTypes.add("Meeting");
		categoryTypes.add("T&M");
		categoryTypes.add("Support");
		categoryTypes.add("Implementation");
		categoryTypes.add("Documentation");
		
		for (String category : categoryTypes) {
			
			IdAndValuePair idAndValuePair = new IdAndValuePair();
			idAndValuePair.setId(id);
			idAndValuePair.setValue(category);
			
			categoryIDandValue.add(idAndValuePair);
			id++;
			
			
		}

		return categoryIDandValue;
	}
	
	
	@GetMapping("/getListOfStatus")
	public List<IdAndValuePair> getStatusTypes() {
			

		List<IdAndValuePair> statusIDandValue = new ArrayList<>();
			List<String> statusTypes = new ArrayList<>();
			Long id = (long) 1; 
			statusTypes.add("Completed");
			statusTypes.add("In Process");
			statusTypes.add("Closed");
			
			for (String category : statusTypes) {
				
				IdAndValuePair idAndValuePair = new IdAndValuePair();
				idAndValuePair.setId(id);
				idAndValuePair.setValue(category);
				
				statusIDandValue.add(idAndValuePair);
				id++;
				
				
			}
			

			return statusIDandValue;
		}
	
	
	// single timesheet approve
	@PostMapping(path = "/complete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response approveTimesheet(Authentication authentication,
			@RequestBody Request request) {
		UserPrincipal up = (UserPrincipal) authentication.getPrincipal();
//		request.getRequestData().put("userId",authentication.getName());
		request.getRequestData().put("userId",up.getEmployeeId());
		Response res = timesheetDelegator.approveTimesheet(request.getRequestData());
		return res;
	}
	
	
	@PutMapping("/bulkApprove")
	public ResponseEntity<?> bulkApprove(HttpServletRequest request,
			@Valid @RequestBody List<TimeSheetEntity> lines, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> message = timesheetService.approveSelectedOrders(lines, request);

		return message;
	}
	
	@GetMapping("/getTimesheetsForApproval")
	public ResponseEntity<?> getTimesheetsForApproval(HttpServletRequest http, Principal principal) 
	{
		
		ResponseEntity<?> message = timesheetService.getPendingTimesheetForApproval(http);

		return message;
	}
	
	
	@GetMapping("/getSingleTimeSheetById/{id}")
	 public ResponseEntity<?> getSingleTimesheet(@PathVariable String id, Principal principal)throws AppException 
	{
		
		ResponseEntity<?> message = timesheetService.getSingleTimesheetById(id);

		return message;
		 
}
	
	
	@GetMapping("/getListOfProjects")
	public List<Projects> getAllProjects(Principal principal) {
	//	getTimesheetsForApproval(null, principal);
		List<Projects> listofProjects = projectsRepository.findAll();
		return listofProjects;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/download/samplefile")
       public ResponseEntity<?> downloadSampleExcel() throws AppException, ExcelException, Exception {

		    String SHEET = "Timesheet_Template";

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Workbook workbook = new XSSFWorkbook();

			InputStreamResource file = null;
			String filename = "Timesheet_Template";
			String extension = ".xlsx";
			
			filename = filename + extension; 

			try {

					Sheet sheet = workbook.createSheet(SHEET);
					
					timesheetService.generateSampleFile(workbook,sheet);
					
					workbook.write(out);

					file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));


			 }catch(Exception ex) {
					String msg = ex.getMessage() != null ? ex.getMessage() : "";

					String cause = "";

					if (ex.getCause() != null) {

						cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

						msg = msg + "!!!" + cause;
						
						  
					}
					return new ResponseEntity(new ApiResponse(false, "Failed to Generate Template File: " + msg),
						HttpStatus.BAD_REQUEST);
			}
			
			
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
		 
		    }
	
	
	 @Value("${file.upload-dir}")
	 String FILE_DIRECTORY;
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @PostMapping("/upload/file")
	 public ResponseEntity<?> uploadExcel(@RequestParam("File") MultipartFile file) {
	 try {
			 Path root = Paths.get(FILE_DIRECTORY);
			 if (!Files.exists(root)) {
	                try {
	    	            Files.createDirectories(Paths.get(FILE_DIRECTORY));
	    	        } catch (IOException e) {
	    	            throw new RuntimeException("Could not create upload folder!");
	    	        }
	            }
			 
		 File uploadedFile = new File(FILE_DIRECTORY+"Timesheet_Template.xlsx");
		 
		 uploadedFile.createNewFile();
		 FileOutputStream fileOut = new FileOutputStream(uploadedFile);
		 fileOut.write(file.getBytes());
		 fileOut.close();

		 timesheetService.readExcelData(uploadedFile);
		 
	 }catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, "File Uploading Failed!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "File Uploaded Successfully"), HttpStatus.CREATED);
	}

	
}
