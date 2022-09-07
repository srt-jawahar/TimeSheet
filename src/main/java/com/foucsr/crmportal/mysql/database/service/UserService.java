package com.foucsr.crmportal.mysql.database.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.BadRequestException;
import com.foucsr.crmportal.exception.ExcelException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.controller.AuthController;
import com.foucsr.crmportal.mysql.database.repository.DesignationRepository;
import com.foucsr.crmportal.mysql.database.repository.RoleRepository;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.DeleteUserRequest;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.SignUpRequest;
import com.foucsr.crmportal.payload.UpdateUserRequest;
import com.foucsr.crmportal.payload.UserProfile;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateLeadRequest;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.EmailSubject;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.SendMail;
import com.foucsr.crmportal.mysql.database.repository.EmailDetailsRepository;
import com.foucsr.crmportal.util.EmailHtmlLoader;

@Service
public class UserService {

	@Autowired
	private UserRepository usersRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthController authController;
	
	@Autowired
	RoleRepository rolerepository;
	
	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@Autowired
	DesignationRepository designationRepository;
	
	@Autowired
	EmailHtmlLoader emailHtmlLoader;
	
	@Autowired
	EmailDetailsRepository emailDetailsRepository;
	
	Logger logger = LoggerFactory.getLogger(UserRepository.class);

	public List<UserProfile> findAllProjects() {
		
		List<UserProfile> userProfiles = new ArrayList<>();
		List<User> userIds = usersRepository.findListOfUsers();
	
		for (User user : userIds) {
		UserRoleEntity roleEntity = userRoleRepo.findByEmpId(user.getEmployeeId()).orElseThrow(() -> new AppException("User does not exist."));
		KpiAndKraGroup grp = kpiAndKraGroupRepository.findById(roleEntity.getGroupId()).orElseThrow(() -> new AppException("Group does not exist."));
		
        Set<Role> roleList = user.getRoles();
        Role newRole = null;
        for (Role roles : roleList) {
		 newRole = roles;
		}
        
       

		UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getEmployeeId(),
			user.getEmail(),newRole.getId(),newRole.getDescription(),user.getAvatarUrl(),user.getIs_Active(),roleEntity.getManagerId(),grp.getName()
			,roleEntity.getDesignation(),grp.getId(),user.getCountry_code(), user.getIs_bulk_upload(), user.getDate_freeze());
		
		
		userProfiles.add(userProfile);
		}
		
		return userProfiles;
	
	}
	
	public void deleteUserById(long id) {

		User user = usersRepository.findById(id).orElseThrow(() -> new AppException("User does not exist."));
		usersRepository.delete(user);
	}
	
	public Optional findUserByResetToken(String resetToken) {
		return usersRepository.findByResetToken(resetToken);
	}
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest signUpRequest)throws AppException{
		  User user = usersRepository.findById(signUpRequest.getId()).orElseThrow(
					() -> new ResourceNotFoundException("User with this Id  does not exist!", "id", signUpRequest.getId()));
			
		String old_employeeId = user.getEmployeeId();
		  
		user.setEmail(signUpRequest.getEmail());
		user.setEmployeeId(signUpRequest.getEmployeeId());
        user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setAvatarUrl(signUpRequest.getAvatarUrl());
		user.setCountry_code(signUpRequest.getCountry_code());
		user.setIs_bulk_upload(signUpRequest.getIs_bulk_upload());
		user.setDate_freeze(signUpRequest.getDate_freeze());
		
		Role userRole = rolerepository.findByName(signUpRequest.getUserRoles())
				.orElseThrow(() -> new AppException("User Role not set."));

		Set rolesMap = new HashSet();
		Role setRoles = new Role();
		setRoles.setId((long) userRole.getId());
		setRoles.setName(userRole.getName());
		user.setRoles(rolesMap);
		Set rolesMap1 = new HashSet();
		rolesMap1.add(userRole);
		user.setRoles(rolesMap1);
		
	
		User results =	usersRepository.save(user);
	
		// update the user manager mapping
			
			
			String role = userRole.getName();
			String role_name = "Team Member";
			
			if("ROLE_MANAGER".equals(role)) {
				
				role_name = "Manager";
			}
			

		if (signUpRequest.getManagerId() != null && !"".equals(signUpRequest.getManagerId())) {

			UserRoleEntity userManMap = null;
			Optional<UserRoleEntity> usrRoleEntity = userRoleRepo.findById(old_employeeId);
			if (usrRoleEntity.isPresent()) {
				userManMap = usrRoleEntity.get();
			}
			
			String employeeId = signUpRequest.getEmployeeId();
			String managerId = signUpRequest.getManagerId();
			
			if(userManMap != null) {
				
				if(employeeId.equals(managerId)) {
					throw new BadRequestException("Same User Cannot be a manager for himself " +employeeId );
				} else {
					userManMap.setManagerId(signUpRequest.getManagerId());
				}
				userManMap.setManagerId(signUpRequest.getManagerId());
				userManMap.setGroupId(signUpRequest.getGroup_id());
				userManMap.setRole(role_name);
				userManMap.setEmpId(employeeId);
				if (signUpRequest.getDesignation() != null && !"".equals(signUpRequest.getDesignation())) {
					
					userManMap.setDesignation(signUpRequest.getDesignation());
				}
				
				userRoleRepository.delete(userManMap);
				
				userRoleRepository.save(userManMap);
			}
		}
		
			
		    sendMail(signUpRequest, user);
			
			
			 return new ResponseEntity(new ApiResponse(true, "user Updated successfully"), HttpStatus.OK);


	  }
	  
	 

	private void sendMail(SignUpRequest signUpRequest,  User user) {
  
			String emailFrom = new String();
			List<String> emailTo = new ArrayList<String>();
			List<String> emailCC = new ArrayList<String>();
			String subject = AppConstants.MAIL_SUBJECT;
			
			String managerName = "";
			String managerId = "";
			String designation ="";
			String role ="";
			
			EmailSubject emailSubject = null;
			String text = emailHtmlLoader.sendUpdateContent(user,signUpRequest.getPassword(), managerName , managerId ,designation , role);

			try {
			UserRoleEntity entity = userRoleRepository.findByEmpId(user.getEmployeeId()).orElseThrow(() -> new AppException("User details does not exist."));
			managerId = entity.getManagerId();
			User manager = usersRepository.findByEmployeeId(managerId).orElseThrow(() -> new AppException("User details does not exist."));
			managerName = manager.getName();
			designation =entity.getDesignation();
			role = entity.getRole();
			
			

			emailTo.add(user.getEmail());
			
				emailSubject = EmailSubject.getInstance(emailDetailsRepository);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidKeySpecException e) {

				throw new AppException("Unable to get Email details");
			}

			String fromEmail = emailSubject.getUsername();
			
			emailFrom = fromEmail;
			
			emailSubject.init(emailFrom, emailTo, emailCC, null, subject, text);
			emailSubject.setHTML(true);
		
			SendMail sm = new SendMail();
			
			sm.sendMail(emailSubject);
		}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> activeOrInactiveUser(DeleteUserRequest deleteRequest) {

		SCAUtil prUtil = new SCAUtil();
		
		Long user_id = deleteRequest.getId();
		char activeOrInactive = deleteRequest.getIs_Active();
		
		try {

			User user = usersRepository.findUserByIdToActiveOrInactive(user_id);
			
			if(user == null) {
				
				return new ResponseEntity(new ApiResponse(false, "User does not exist" ),
						HttpStatus.BAD_REQUEST);
			}
			
			user.setIs_Active(activeOrInactive);
			
			usersRepository.save(user);
			
		} catch (Exception e) {

			logger.error("***************** Unable to Active/Inactive the user!  *********************\n" + e);

			String msg = prUtil.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Active/Inactive the user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> multipleActiveInActive(MultipleSelectRequest multipleSelectRequest) {
		
		
			
			Long ids[] = multipleSelectRequest.getIds();
		
    	    for(int i=0;i<ids.length;i++) {
    		
    		
		     if(!(usersRepository.existsById(ids[i]))) {
					
					return new ResponseEntity(new ApiResponse(false, "User does not exist."),HttpStatus.BAD_REQUEST);
			 }
    	    }
		SCAUtil prUtil = new SCAUtil();
		
		Long[] user_id = multipleSelectRequest.getIds();
		char activeOrInactive = multipleSelectRequest.getIs_Active();
    	    
		try{

			User user = usersRepository.findUsersByIdToActiveOrInactive(user_id);
			
			if(user == null) {
				
				return new ResponseEntity(new ApiResponse(false, "User does not exist" ),
						HttpStatus.BAD_REQUEST);
			}
			
			user.setIs_Active(activeOrInactive);
			
			usersRepository.save(user);
		
		
		} catch (Exception e) {

			logger.error("***************** Unable to Active/Inactive the user!  *********************\n" + e);

		    String msg = prUtil.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Active/Inactive the user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);

	}
	
	public void setasActive(long id) {

		User user = usersRepository.findById(id).orElseThrow(() -> new AppException("User does not exist."));
		user.setIs_Active('Y');
		usersRepository.save(user);
	}
	
	public void setasInActive(long id) {

		User user = usersRepository.findById(id).orElseThrow(() -> new AppException("User does not exist."));
		user.setIs_Active('N');
		usersRepository.save(user);
	}
	
	public void generateSampleFile(Workbook workbook,Sheet sheet1) throws Exception, AppException,ExcelException {
		
		Sheet sheet = sheet1;
		
            XSSFFont Header_Type_Font = (XSSFFont) workbook.createFont();
		
            Header_Type_Font.setBold(true);
	    	Header_Type_Font.setColor(IndexedColors.BLACK.index);
		
            XSSFCellStyle HeaderCellStyle = (XSSFCellStyle) workbook.createCellStyle();
		    HeaderCellStyle.setFont(Header_Type_Font);
		    
		    Row row;
		    
		    row = sheet.createRow(0);
		    
			Cell cell;  
			cell = row.createCell(0);
			cell.setCellValue("S.No");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue("Employee ID");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue("Employee Name");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue("Employee Role");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue("Employee Email ID");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue("Employee Designation");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue("Manager ID");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue("KPI/KRA Group name");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue("Password");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(9);
			cell.setCellValue("Bulk Upload");
			cell.setCellStyle(HeaderCellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue("Date Freeze");
			cell.setCellStyle(HeaderCellStyle);
			
				
            row = sheet.createRow(1);
		   
			cell = row.createCell(0);
			cell.setCellValue("1");
			
			cell = row.createCell(1);
			cell.setCellValue("M1031");
			
			cell = row.createCell(2);
			cell.setCellValue("Smith");
			
			cell = row.createCell(3);
			cell.setCellValue("ROLE_MANAGER");
			
			cell = row.createCell(4);
			cell.setCellValue("Erwin@anymail.com");
			
			cell = row.createCell(5);
			cell.setCellValue("Full Stack developer - Trainee");
			
			cell = row.createCell(6);
			cell.setCellValue("M1003");
			
			cell = row.createCell(7);
			cell.setCellValue("Trainee Consult");
			
			cell = row.createCell(8);
			cell.setCellValue("StronGpaSSword");
			
			cell = row.createCell(9);
			cell.setCellValue("Y");
			
			cell = row.createCell(10);
			cell.setCellValue("8");
			
			 row = sheet.createRow(2);
			   
				cell = row.createCell(0);
				cell.setCellValue("2");
				
				cell = row.createCell(1);
				cell.setCellValue("M1033");
				
				cell = row.createCell(2);
				cell.setCellValue("vicky");
				
				cell = row.createCell(3);
				cell.setCellValue("ROLE_TEAM_MEMBER");
				
				cell = row.createCell(4);
				cell.setCellValue("vicky@anymail.com");
				
				cell = row.createCell(5);
				cell.setCellValue("Associate - FullStack Developer");
				
				cell = row.createCell(6);
				cell.setCellValue("M1003");
				
				cell = row.createCell(7);
				cell.setCellValue("Associate Consult");
				
				cell = row.createCell(8);
				cell.setCellValue("StronGpaSSword");
				
				cell = row.createCell(9);
				cell.setCellValue("N");
				
				cell = row.createCell(10);
				cell.setCellValue("2");
				
		    
				for(int i=0;i<30;i++) {
					sheet.autoSizeColumn(i);
					}
	   }

       public void readExcelData(File file) throws EncryptedDocumentException, IOException{
	
		
		Workbook workbook = WorkbookFactory.create(file);
		workbook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
	
		
		for(Sheet sheet :workbook ) {
			for(Row row : sheet) {
				
		if(!(row.getRowNum()==0)) {
			
			if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
				break; 

			}
				
			
			DataFormatter formatter = new DataFormatter(); 
			
			
			String rowNos = formatter.formatCellValue(row.getCell(0));
			Long rowNo = Long.parseLong(rowNos);
			
			
		SignUpRequest signUpRequest = new SignUpRequest();	
		
		signUpRequest.setEmployeeId(formatter.formatCellValue(row.getCell(1)));
		signUpRequest.setName(formatter.formatCellValue(row.getCell(2)));
		signUpRequest.setUserRoles(formatter.formatCellValue(row.getCell(3)));
		signUpRequest.setUsername(formatter.formatCellValue(row.getCell(1)));
		signUpRequest.setEmail(formatter.formatCellValue(row.getCell(4)));
		signUpRequest.setDesignation(formatter.formatCellValue(row.getCell(5)));
		signUpRequest.setManagerId(formatter.formatCellValue(row.getCell(6)));
		
		String grpName = formatter.formatCellValue(row.getCell(7));
		
		
		if(row.getCell(7)==null) {
			if(rowNo ==1) {
				throw new AppException("Group Name cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Group Name cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
        KpiAndKraGroup kpiAndKraGroup = kpiAndKraGroupRepository.findByName(grpName);
		
		signUpRequest.setGroup_id(kpiAndKraGroup.getId());
		signUpRequest.setPassword(formatter.formatCellValue(row.getCell(8)));
		signUpRequest.setIs_bulk_upload(formatter.formatCellValue(row.getCell(9)));
		if(row.getCell(10) == null || row.getCell(10).getCellType() == CellType.BLANK) {
			signUpRequest.setDate_freeze(0);
		} else {
			signUpRequest.setDate_freeze(Integer.parseInt(formatter.formatCellValue(row.getCell(10))));
		}
		
		
		if((signUpRequest.getName()==null) || (signUpRequest.getName()=="")){
			if(rowNo ==1) {
				throw new AppException("Name cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Name cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		if((signUpRequest.getEmployeeId()==null)|| (signUpRequest.getEmployeeId()=="")) {
			if(rowNo ==1) {
				throw new AppException("EmployeeId cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("EmployeeId cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		if((signUpRequest.getEmail()==null) || (signUpRequest.getEmail()=="")){
			if(rowNo ==1) {
				throw new AppException("Email cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Email cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		if((signUpRequest.getPassword()==null)|| (signUpRequest.getPassword()=="")) {
			if(rowNo ==1) {
				throw new AppException("Password cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Password cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		if((signUpRequest.getManagerId()==null)|| (signUpRequest.getManagerId()=="")) {
			if(rowNo ==1) {
				throw new AppException("ManagerId cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("ManagerId cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		
		if((signUpRequest.getUserRoles()==null) || (signUpRequest.getUserRoles()=="")) {
			if(rowNo ==1) {
				throw new AppException("UserRoles cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("UserRoles cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		

		if((signUpRequest.getIs_bulk_upload()==null) || (signUpRequest.getIs_bulk_upload()=="")) {
			if(rowNo ==1) {
				throw new AppException("Bulk Upload cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Bulk Upload cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		
		
		
		if((signUpRequest.getDesignation()==null) || (signUpRequest.getDesignation()=="")){
			if(rowNo ==1) {
				throw new AppException("Designation cannot be blank for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Designation cannot be blank for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
			
		}
		if(!(signUpRequest.getEmployeeId()==null)) {
	    	if(usersRepository.existsByEmployeeId(signUpRequest.getEmployeeId())) {
	    		if(rowNo==1) {
	        		throw new AppException("Employee ID is already in use for the User at S.No : " +rowNo);

	    		}else {
	    	
	    		throw new AppException("Employee ID is already in use for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
	    	}
	    	}
	    	}
		
		if(usersRepository.existsByUsername(signUpRequest.getUsername())) {
			if(rowNo ==1) {
				throw new AppException("Username is already taken for the User at S.No : " +rowNo);

			}else {
			throw new AppException("Username is already taken for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
			}
    	}
    	if(!(signUpRequest.getEmail()==null)) {
    	if(usersRepository.existsByEmail(signUpRequest.getEmail())) {
    		if(rowNo==1)
    		{
    			throw new AppException("Email is already in use for the User at S.No : " +rowNo);

    		}else {
			throw new AppException("Email is already in use for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
    		}
    	}
    	}
    	
    	
    	
    	if(!(signUpRequest.getGroup_id()==null)) {
        	if(!(kpiAndKraGroupRepository.existsById(signUpRequest.getGroup_id()))) {
        		if(rowNo==1) {
            		throw new AppException("Group With this Id does not exist for the User at S.No : " +rowNo);

        		}else {
        	
        		throw new AppException("Group With this Id does not exist for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
        	}
        	}
    	}
        	
        	if(!(signUpRequest.getDesignation()==null)) {
            	if(!(designationRepository.existsByDesignation(signUpRequest.getDesignation()))) {
            		if(rowNo==1) {
                		throw new AppException("Designation does not exist does not exist for the User at S.No : " +rowNo);

            		}else {
            	
            		throw new AppException("Designation does not exist does not exist for the User at S.No : " +rowNo+", Details uploaded succesfully for users till S.No : "+(rowNo-1));
            	}
            	}
            	}
        	
        	
        	
    
    	
    	
    	User user = new User(signUpRequest.getEmployeeId(),signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword(), 'Y',signUpRequest.getAvatarUrl());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDate_freeze(signUpRequest.getDate_freeze());
		user.setIs_bulk_upload(signUpRequest.getIs_bulk_upload());
		//String groups[] = new String[] {"Default Group"}; 
		
		
		
		
		Role userRole = rolerepository.findByName(signUpRequest.getUserRoles())
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));
		
		User results = usersRepository.save(user);
		
		// save the user manager mapping
		String role = userRole.getName();
		String role_name = "Team Member";
		
		if("ROLE_MANAGER".equals(role)) {
			
			role_name = "Manager";
		}
		
		UserRoleEntity userManMap = new UserRoleEntity();
		userManMap.setEmpId(signUpRequest.getEmployeeId());
		userManMap.setManagerId(signUpRequest.getManagerId());
		userManMap.setDesignation(signUpRequest.getDesignation());
		userManMap.setRole(role_name);
		userManMap.setGroupId(signUpRequest.getGroup_id());
		
		userRoleRepository.save(userManMap);
		
		
		}
			}
		}
		
	}
       
       
       @Cacheable(cacheNames="employees")
   	public Map<String, Object> getEmp() {
   		List<User> usrEntityRslts = usersRepository.findAll();
   		List<UserRoleEntity> usrRoleEntityRslts = userRoleRepo.findAll();
   		
   		Map<String, Object> usrRoleDataList = new HashMap<String, Object>();
   

   		if (!usrRoleEntityRslts.isEmpty()) {
   			usrRoleEntityRslts.forEach(userRoleEntity -> {
   				Map<String, String> usrDetails = new HashMap<String, String>();
   				usrDetails.put("role", userRoleEntity.getRole());
   				usrDetails.put("designation", userRoleEntity.getDesignation());
   				usrDetails.put("managerId", userRoleEntity.getManagerId());
   				
   				usrRoleDataList.put(userRoleEntity.getEmpId(), usrDetails);
   			});
   		}
   		
   		
   		Map<String, Object> usrDataList = new HashMap<String, Object>();

   		if (!usrEntityRslts.isEmpty()) {
   			usrEntityRslts.forEach(userEntity -> {
   				Map<String, String> usrDetails = new HashMap<String, String>();
   				Map<String, String> usrRoleMap = new HashMap<String, String>();
   				usrDetails.put("name", userEntity.getName());
   				usrDetails.put("id", userEntity.getEmployeeId());
   				usrDetails.put("mailId", userEntity.getEmail());
   				
   				usrRoleMap = (Map<String, String>) usrRoleDataList.get(userEntity.getEmployeeId().toUpperCase());
   				
   				if(usrRoleMap != null ) {
   					usrDetails.put("role", usrRoleMap.get("role"));
   					usrDetails.put("designation", usrRoleMap.get("designation"));
   					usrDetails.put("managerId", usrRoleMap.get("managerId"));

   					usrDetails.put("managerName", this.getUserNameForReport((String) usrRoleMap.get("managerId")));
   				
   				}
   				usrDataList.put(userEntity.getEmployeeId(), usrDetails);
   			});
   		}

   		return usrDataList;
   	}
       
       public String getUserName(String userId) {

   		Optional<User> userEntity = usersRepository.findById(userId);

   		String name = "";
   		if (userEntity.isPresent()) {
   			name = userEntity.get().getName();
   		}

   		return name;

   	}
       
       public String getRole(String userId) {

   		String role = "";

   		Optional<UserRoleEntity> usrRoleEntity = userRoleRepo.findById(userId);
   		if (usrRoleEntity.isPresent()) {
   			role = usrRoleEntity.get().getRole();
   		}

   		return role;

   	}
       
       public String getManager(String empId) {

   		String managerId = "";

   		Optional<UserRoleEntity> usrRoleEntity = userRoleRepo.findById(empId);
   		if (usrRoleEntity.isPresent()) {
   			managerId = usrRoleEntity.get().getManagerId();
   		}

   		return managerId;

   	}
       
      
       public String getUserNameForReport(String userId) {
 
    	   
      		Optional<User> userEntity = usersRepository.findByUsernameOrEmployeeId(userId, userId);
      		
      		String name = "";
      		if (userEntity.isPresent()) {
      			
      			name = userEntity.get().getName();
      			
      		}

      		return name;

      	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<?> updatePasseord(Long userIdFromRequest, String password) {
		
		  if(!(usersRepository.existsById(userIdFromRequest))) {
  		    return new ResponseEntity(new ApiResponse(false,"User does not exist"),HttpStatus.BAD_REQUEST);  
  	    }
		User newUser = usersRepository.findById(userIdFromRequest).orElseThrow(() -> new AppException("User does not exist."));
		
		newUser.setPassword(passwordEncoder.encode(password));
		
		usersRepository.save(newUser);
		
		return new ResponseEntity(new ApiResponse(true, "Password updated successfully "), HttpStatus.OK);


		
		
	}


}
	

