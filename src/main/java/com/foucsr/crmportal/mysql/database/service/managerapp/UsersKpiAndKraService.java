package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.xalan.templates.ElemForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiKraRating;
import com.foucsr.crmportal.mysql.database.model.managerapp.UsersKpiAndKra;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.UsersKpiAndKraRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.UserProfile;
import com.foucsr.crmportal.payload.managerapp.ManagerPendingApproval;

@Service
public class UsersKpiAndKraService {
	
	@Autowired
	private UsersKpiAndKraRepository usersKpiAndKraRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired 
	KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> createUsersKraKpi(@Valid @RequestBody UsersKpiAndKra usersKraAndKpi)throws AppException{
    	
    	
		 
		if(!(userRepository.existsById(usersKraAndKpi.getUserId()))) {
    		return new ResponseEntity(new ApiResponse(false,"User does not exist "),HttpStatus.BAD_REQUEST);  
    	}
			 
		      User newUser = userRepository.findById(usersKraAndKpi.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));	
		      UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));	
		      
			
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, 5);
			c.add(Calendar.MINUTE, 30);
			Date newTime = c.getTime();
			
			String status;
			
			if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("save")) {
				status = "DRAFT";
			} else if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("submit")) {
				status = "SUBMITTED";
			} else {
	    		return new ResponseEntity(new ApiResponse(false,"Status does not exist "),HttpStatus.BAD_REQUEST);  

			}
			    	
    	
	    	UsersKpiAndKra usersKpiAndKra = new UsersKpiAndKra(usersKraAndKpi.getUserId(),'Y',usersKraAndKpi.getKpiList(),
	    			status,sdf.format(newTime),entity.getManagerId());
	    	
	     if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("submit")) {
				usersKpiAndKra.setSubmittedDate(sdf.format(newTime));
			} 
	     if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("approve")) {
				usersKpiAndKra.setApprovedDate(sdf.format(newTime));
			}
	    	
	    	usersKpiAndKraRepository.save(usersKpiAndKra);
   
    	 return new ResponseEntity(new ApiResponse(true, "Users Kpi/Kra rating Created successfully"), HttpStatus.CREATED);
    	
    	
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> updateUsersKraKpi(@Valid @RequestBody UsersKpiAndKra usersKraAndKpi,@PathVariable String month, @PathVariable String year)throws AppException{
    	
		 
		if(!(userRepository.existsById(usersKraAndKpi.getUserId()))) {
    		return new ResponseEntity(new ApiResponse(false,"User does not exist taken"),HttpStatus.BAD_REQUEST);  
    	}
		
		  User newUser = userRepository.findById(usersKraAndKpi.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));	
	      UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));	
	      
			
			
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, 5);
			c.add(Calendar.MINUTE, 30);
			Date newTime = c.getTime();
			
			String status;
			
			if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("save")) {
				status = "DRAFT";
			} else if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("submit")) {
				status = "SUBMITTED";
			} else {
	    		return new ResponseEntity(new ApiResponse(false,"Status does not exist "),HttpStatus.BAD_REQUEST);  

			}
			    	
    	
	    	UsersKpiAndKra usersKpiAndKra = usersKpiAndKraRepository.findById(usersKraAndKpi.getId()).orElseThrow(() -> new AppException("Kpi/Kra details does not exist."));

	    	usersKpiAndKra.setUserId(usersKraAndKpi.getUserId());
	    	usersKpiAndKra.setIsUserActive('Y');
	    	usersKpiAndKra.setKpiList(usersKraAndKpi.getKpiList());
	    	usersKpiAndKra.setStatus(status);
	    	usersKpiAndKra.setManagerId(entity.getManagerId());
	    	usersKpiAndKra.setDate(usersKraAndKpi.getDate());
	    	
	      	
		     if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("submit")) {
					usersKpiAndKra.setSubmittedDate(sdf.format(newTime));
					
				} 
		   

	    	
	    			
//	    	usersKpiAndKra = UsersKpiAndKra(usersKraAndKpi.getId(),usersKraAndKpi.getUserId(),'Y',usersKraAndKpi.getKpiList(),
//	    			status,sdf.format(newTime));
	    	
	    	
	    	usersKpiAndKraRepository.save(usersKpiAndKra);
   
    	 return new ResponseEntity(new ApiResponse(true, "Users Kpi/Kra rating Updated successfully"), HttpStatus.OK);
    	
    	
    }

	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> approveUsersKraKpi(@Valid @RequestBody UsersKpiAndKra usersKraAndKpi)throws AppException{
    	

		 
		if(!(userRepository.existsById(usersKraAndKpi.getUserId()))) {
    		return new ResponseEntity(new ApiResponse(false,"User does not exist taken"),HttpStatus.BAD_REQUEST);  
    	}
		
		  User newUser = userRepository.findById(usersKraAndKpi.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));	
	      UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));	
	      
			
			
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, 5);
			c.add(Calendar.MINUTE, 30);
			Date newTime = c.getTime();
			
			String status;
			
			if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("approve")) {
				status = "APPROVED";
			} else {
	    		return new ResponseEntity(new ApiResponse(false,"Status does not exist "),HttpStatus.BAD_REQUEST);  

			}
			    	
    	
	    	UsersKpiAndKra usersKpiAndKra = usersKpiAndKraRepository.findById(usersKraAndKpi.getId()).orElseThrow(() -> new AppException("Kpi/Kra details does not exist."));

	    	usersKpiAndKra.setUserId(usersKraAndKpi.getUserId());
	    	usersKpiAndKra.setIsUserActive('Y');
	    	usersKpiAndKra.setKpiList(usersKraAndKpi.getKpiList());
	    	usersKpiAndKra.setStatus(status);
	    	usersKpiAndKra.setManagerId(entity.getManagerId());
	    	
	      	 
		     if ((usersKraAndKpi.getStatus()).equalsIgnoreCase("approve")) {
					usersKpiAndKra.setApprovedDate(sdf.format(newTime));
				}

	    	
	    			
//	    	usersKpiAndKra = UsersKpiAndKra(usersKraAndKpi.getId(),usersKraAndKpi.getUserId(),'Y',usersKraAndKpi.getKpiList(),
//	    			status,sdf.format(newTime));
	    	
	    	
	    	usersKpiAndKraRepository.save(usersKpiAndKra);
   
    	 return new ResponseEntity(new ApiResponse(true, "Users Kpi/Kra rating Approved successfully"), HttpStatus.OK);
    	
    	
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> getUsersKpiKraForaMonth(Long userIdFromRequest, String month, String year) {
		
		       String date = year.concat("/").concat(month);
		       
		       UsersKpiAndKra newGrp =  usersKpiAndKraRepository.findlistofUsersKpiKraRating(userIdFromRequest, date);
		       if(!(newGrp == null)) {
		 		return  ResponseEntity.status(HttpStatus.OK).body(newGrp); 
		       } else {
		    	   User newUser = userRepository.findById(userIdFromRequest).orElseThrow(() -> new AppException("User details does not exist."));
		    	   String empId = newUser.getEmployeeId();
				    UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));	

		    	   KpiAndKraGroup res =  kpiAndKraGroupRepository.findListOfKpiGroupsforUser(empId);  
		    	   if(!(res == null)) {
                 
		    		   UsersKpiAndKra userKpiKra = new UsersKpiAndKra();
		    		   
		    		   Set<KpiAndKra> kpiList = res.getKpiAndKra();
		    		   List<KpiKraRating> kpiRatingList = new ArrayList<KpiKraRating>();
		    		   
		    		   
		    		   for (KpiAndKra kpiAndKra : kpiList) {
		    			   
		    			   System.out.println(kpiAndKra.getName());
		    			   
		    			   KpiKraRating newRating = new KpiKraRating();
		    			   newRating.setDescription(kpiAndKra.getDescription());
		    			   newRating.setKpi(kpiAndKra.getName());
		    			   newRating.setRating(kpiAndKra.getRating());
		    			   newRating.setSelfrating((long) 0);
		    			   newRating.setManagerRating((long) 0);
		    			   
		    			   kpiRatingList.add(newRating);
		    			  }
		    		   
		    		   userKpiKra.setStatus("DRAFT");
		    		   userKpiKra.setUserId(userIdFromRequest);
		    		   userKpiKra.setKpiList(kpiRatingList);
		    		   userKpiKra.setDate(date);
		    		   userKpiKra.setIsUserActive('Y');
		               userKpiKra.setManagerId(entity.getManagerId());
		               
			             usersKpiAndKraRepository.save(userKpiKra);
		    		   
		    		   
		    		   
		    UsersKpiAndKra newGrp2 =  usersKpiAndKraRepository.findlistofUsersKpiKraRating(userIdFromRequest, date);
				   
		    if(!(newGrp2 == null)) {
			return  ResponseEntity.status(HttpStatus.OK).body(newGrp2); 
				      
		    }else {
   	    	 return new ResponseEntity(new ApiResponse(false, "Unable to get Users Kpi/Kra details"), HttpStatus.BAD_REQUEST);
	         }
		    
		    
	 }
		    else {
		    	    	 return new ResponseEntity(new ApiResponse(false, "Unable to get Users Kpi/Kra details"), HttpStatus.BAD_REQUEST);
	    	   }

	  
		    	   
    }
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> getPendingKpiApproval(Long userIdFromRequest) 
	{
		
		List<UsersKpiAndKra> kpiApproval = new ArrayList<UsersKpiAndKra>();
		List<ManagerPendingApproval> managerApproval = new ArrayList<ManagerPendingApproval>();
		
		try {

			   User newUser = userRepository.findById(userIdFromRequest).orElseThrow(() -> new AppException("User details does not exist."));
			        String managerid = newUser.getEmployeeId();
			        
			      
			        kpiApproval = usersKpiAndKraRepository.getPendingKpi(managerid);
			        
			        
			        for (UsersKpiAndKra usersKpiAndKra : kpiApproval) {
			        	
			        	      User user = userRepository.findById(usersKpiAndKra.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));
			  			      UserRoleEntity userRole = userRoleRepository.findByEmpId(user.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));
			  			      ManagerPendingApproval newProfile = new ManagerPendingApproval();
						       newProfile.setDesignation(userRole.getDesignation());
						       newProfile.setEmployeeId(userRole.getEmpId());
						       newProfile.setName(user.getName());
						       newProfile.setUsername(user.getUsername());
						       newProfile.setUsersKpiAndKra(usersKpiAndKra);
						       newProfile.setDate(usersKpiAndKra.getDate());
						       newProfile.setStatus(usersKpiAndKra.getStatus());
						       newProfile.setSubmittedDate(usersKpiAndKra.getSubmittedDate());
						       
						       managerApproval.add(newProfile);
						       
			  			      
					}
					
					if (managerApproval == null) {

						managerApproval = new ArrayList();
					}

				
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to get Kpi and Kra Details!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		
		return  ResponseEntity.status(HttpStatus.OK).body(managerApproval);
		 
	}


	   public void generateReport(Workbook workbook, Sheet sheet_1, String month, String year) {
		
		
		     String date = year.concat("/").concat(month);
		     
		     String[] months = {"null","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		     
			int month1 = (int) Long.parseLong(month);
//			XSSFWorkbook workbook = new XSSFWorkbook(); 
//			 Sheet sheet = workbook.createSheet();
			
			Sheet sheet =sheet_1;
			
			
	XSSFFont Header_Type_Font = (XSSFFont) workbook.createFont();
			
	        Header_Type_Font.setBold(true);
			Header_Type_Font.setColor(IndexedColors.BLACK.index);
			
	XSSFCellStyle HeaderCellStyle = (XSSFCellStyle) workbook.createCellStyle();
			
			HeaderCellStyle.setFont(Header_Type_Font);
			
	XSSFCellStyle TitleCellStyle = (XSSFCellStyle) workbook.createCellStyle();
			
	TitleCellStyle.setFont(Header_Type_Font);
	TitleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
			
			
			Row row;
			int rn = 0;
			
			row = sheet.createRow(rn);
			Cell cell;  
			cell = row.createCell(0);
			cell.setCellValue("KPI/KRA Monthly report");
			cell.setCellStyle(TitleCellStyle);
			rn++;
			
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
			
			
			row = sheet.createRow(rn);
			cell = row.createCell(0);
			cell.setCellValue("Month");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(months[month1]);
			
			cell = row.createCell(2);
			cell.setCellValue("Year");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(year);
			rn++;
			rn++;
			
			
		
			
			row = sheet.createRow(rn);
			
			cell = row.createCell(0);
			cell.setCellValue("S.No");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue("Employee Name");
			cell.setCellStyle(HeaderCellStyle);
			
			
			
			cell = row.createCell(2);
			cell.setCellValue("Employee ID");
			cell.setCellStyle(HeaderCellStyle);
			
	
			cell = row.createCell(3);
			cell.setCellValue("Reporting Manager");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue("Manager ID");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue("Self Rating");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue("Manager Rating");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue("KPI/KRA Group name");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue("Status");
			cell.setCellStyle(HeaderCellStyle);
			
	        rn++;
			
		   List<UsersKpiAndKra> usersKpiAndKraList = usersKpiAndKraRepository.findListOfUsersKpiForAMonth(date);

	        Long size = (long) usersKpiAndKraList.size();
			
			for(int i=0;i<size;i++) {
				
	       row = sheet.createRow(rn);
	       
	       UsersKpiAndKra usersKpiAndKra =usersKpiAndKraList.get(i);
	       
	       User newUser = userRepository.findById(usersKpiAndKra.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));
    	   String empId = newUser.getEmployeeId();
		    UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));
		    User manager = userRepository.findByEmployeeId(entity.getManagerId()).orElseThrow(() -> new AppException("User details does not exist."));
		    KpiAndKraGroup kpiAndKraGroup = kpiAndKraGroupRepository.findById(entity.getGroupId()).orElseThrow(() -> new AppException("KPI/KRA Group details does not exist."));
		    
		    List<KpiKraRating> ratingList = usersKpiAndKra.getKpiList();
		    Long selfRating = (long) 0;
		    Long managerRating = (long) 0;
		    for (KpiKraRating kpiKraRating : ratingList) {
		    	
		    	selfRating = selfRating + kpiKraRating.getSelfrating();
		    	managerRating = managerRating + kpiKraRating.getManagerRating();
		    }
		    
		    
		    
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			
			cell = row.createCell(1);
			cell.setCellValue(newUser.getName());
			
			cell = row.createCell(2);
			cell.setCellValue(empId);
			
			cell = row.createCell(3);
			cell.setCellValue(manager.getName());
			
			cell = row.createCell(4);
			cell.setCellValue(entity.getManagerId());
			
			cell = row.createCell(5);
			cell.setCellValue(selfRating);
			
			cell = row.createCell(6);
			cell.setCellValue(managerRating);
			
			cell = row.createCell(7);
			cell.setCellValue(kpiAndKraGroup.getName());
			
			cell = row.createCell(8);
			cell.setCellValue(usersKpiAndKra.getStatus());
			
			
			
			
	        rn++;

			}
			
			for(int i=0;i<30;i++) {
				sheet.autoSizeColumn(i);
				}
		
	
		
	}


	public void generateReportForUser(Workbook workbook, Sheet sheet_1, String month, String year, Long userId) {
		
	     String date = year.concat("/").concat(month);
	     
	     String[] months = {"null","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	     
		int month1 = (int) Long.parseLong(month);
//		XSSFWorkbook workbook = new XSSFWorkbook(); 
//		 Sheet sheet = workbook.createSheet();
		
		Sheet sheet =sheet_1;
		
		
        XSSFFont Header_Type_Font = (XSSFFont) workbook.createFont();
		
        Header_Type_Font.setBold(true);
		Header_Type_Font.setColor(IndexedColors.BLACK.index);
		
       XSSFCellStyle HeaderCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		
		HeaderCellStyle.setFont(Header_Type_Font);
		
		  XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
  		style.setWrapText(true);
		
        XSSFCellStyle TitleCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		
            TitleCellStyle.setFont(Header_Type_Font);
            TitleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		
		
		Row row;
		int rn = 0;
		
		row = sheet.createRow(rn);
		Cell cell;  
		cell = row.createCell(0);
		cell.setCellValue("KPI/KRA Monthly report");
		cell.setCellStyle(TitleCellStyle);
		rn++;
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
		
		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("Month");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(months[month1]);
		rn++;
		
		row = sheet.createRow(rn);
		
		cell = row.createCell(0);
		cell.setCellValue("Year");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(year);
		rn++;
		
		
		
		 UsersKpiAndKra usersKpiAndKra = usersKpiAndKraRepository.findlistofUsersKpiKraRating(userId,date);
	     User newUser = userRepository.findById(usersKpiAndKra.getUserId()).orElseThrow(() -> new AppException("User details does not exist."));
		 UserRoleEntity entity = userRoleRepository.findByEmpId(newUser.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));
		 KpiAndKraGroup kpiAndKraGroup = kpiAndKraGroupRepository.findById(entity.getGroupId()).orElseThrow(() -> new AppException("KPI/KRA Group details does not exist."));

		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("Name");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(newUser.getName());
		rn++;
		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("Employee ID");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(newUser.getEmployeeId());
		rn++;
		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("Reporting manager Emp ID");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(entity.getManagerId());
		rn++;
		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("KPI/KRA Group name");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(kpiAndKraGroup.getName());
		rn++;
		
		row = sheet.createRow(rn);
		cell = row.createCell(0);
		cell.setCellValue("Status");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(usersKpiAndKra.getStatus());
		rn++;
	    rn++;
		

		row = sheet.createRow(rn);
		
		cell = row.createCell(0);
		cell.setCellValue("S.No");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue("KPI/KRA");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Description");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("Rating");
		cell.setCellStyle(HeaderCellStyle);
			
		cell = row.createCell(4);
		cell.setCellValue("Self Rating");
		cell.setCellStyle(HeaderCellStyle);
		
		cell = row.createCell(5);
		cell.setCellValue("Manager Rating");
		cell.setCellStyle(HeaderCellStyle);
	   
		rn++;
		
		 List<KpiKraRating> ratingList = usersKpiAndKra.getKpiList();
		    Long selfRating = (long) 0;
		    Long rating = (long) 0;
		    Long managerRating = (long) 0;
		    int sNo = 1;
		    for (KpiKraRating kpiKraRating : ratingList) {
		    	
		    	row = sheet.createRow(rn);
				
				cell = row.createCell(0);
				cell.setCellValue(sNo);
				
				cell = row.createCell(1);
				cell.setCellValue(kpiKraRating.getKpi());
				
				cell = row.createCell(2);
				cell.setCellValue(kpiKraRating.getDescription());
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(kpiKraRating.getRating());
					
				cell = row.createCell(4);
				cell.setCellValue(kpiKraRating.getSelfrating());
				
				cell = row.createCell(5);
				cell.setCellValue(kpiKraRating.getManagerRating());
			   
				sNo++;
				rn++;
		    	
		    	selfRating = selfRating + kpiKraRating.getSelfrating();
		    	managerRating = managerRating + kpiKraRating.getManagerRating();
		    }
		    
		    row = sheet.createRow(rn);
			
			cell = row.createCell(3);
			cell.setCellValue("Total");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(selfRating);
			
			cell = row.createCell(5);
			cell.setCellValue(managerRating);
			
			for(int i=0;i<30;i++) {
				sheet.autoSizeColumn(i);
				}
		    
		
		
	       }

	
	

}
