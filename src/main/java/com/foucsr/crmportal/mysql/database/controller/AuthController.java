package com.foucsr.crmportal.mysql.database.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.BadRequestException;
import com.foucsr.crmportal.exception.ExcelException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveBalanceEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.DesignationRepository;
import com.foucsr.crmportal.mysql.database.repository.EmailDetailsRepository;
import com.foucsr.crmportal.mysql.database.repository.RoleRepository;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.LeaveBalanceRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.LeaveMasterRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.JwtAuthenticationResponse;
import com.foucsr.crmportal.payload.LoginRequest;
import com.foucsr.crmportal.payload.SignUpRequest;
import com.foucsr.crmportal.payload.UpdateUserRequest;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.EmailHtmlLoader;
import com.foucsr.crmportal.util.EmailSubject;
import com.foucsr.crmportal.util.LeaveAssignUserRequest;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.SendMail;

/**
 * Created by FocusR.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	DesignationRepository designationRepository;

//	@Autowired
//	private POAgentsOracleRepository poAgentsOracleRepositoryOracle;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	EmailDetailsRepository emailDetailsRepository;

	@Autowired
	EmailHtmlLoader emailHtmlLoader;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	SCAUtil scaUtil;
	
	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;
	
	@Autowired
	private LeaveMasterRepository leaveMasterRepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> processForgotPasswordForm(@Valid @RequestBody UpdateUserRequest forgetPassRequest,
			BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

		if (errorMap != null)
			return errorMap;

		User user = userRepository.findByUsernameOrEmail(forgetPassRequest.getEmail(), forgetPassRequest.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User  does not exist!", "username or email",
						forgetPassRequest.getEmail()));

		String jwt = tokenProvider.generateTokenForgetPassword(user);

		user.setResetToken(jwt);

		// Save token to database
		User results = userRepository.save(user);

		sendMail(forgetPassRequest, jwt, user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(results.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Email Sent Successfully"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/updateNewPassword")
	public ResponseEntity<?> updateNewPassword(@Valid @RequestBody UpdateUserRequest signUpRequest,
			BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		User user = userRepository.findByResetToken(signUpRequest.getToken()).orElseThrow(
				() -> new ResourceNotFoundException("User token does not exist!", "token", signUpRequest.getToken()));

		if (signUpRequest.getPassword() != null) {

			String jwt = signUpRequest.getToken();

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

				/*
				 * Long userId = tokenProvider.getUserIdFromJWT(jwt);
				 * 
				 * UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				 * UsernamePasswordAuthenticationToken authentication = new
				 * UsernamePasswordAuthenticationToken(userDetails, null,
				 * userDetails.getAuthorities()); authentication.setDetails(new
				 * WebAuthenticationDetailsSource().buildDetails(request));
				 * 
				 * SecurityContextHolder.getContext().setAuthentication(authentication);
				 */

				user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
				user.setResetToken(null);

				User results = userRepository.save(user);

				URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
						.buildAndExpand(results.getUsername()).toUri();

				return ResponseEntity.created(location)
						.body(new ApiResponse(true, "User password updated successfully"));
			}

		}

		return new ResponseEntity(new ApiResponse(false, "unable to update password"), HttpStatus.BAD_REQUEST);

	}

	private void sendMail(UpdateUserRequest forgetPassRequest, String jwt, User user) {

//		User admin = userRepository.findAdmin()
//				.orElseThrow(() -> new ResourceNotFoundException("User  does not exist!", "", ""));

		String appUrl = forgetPassRequest.getForgetPasswordUrl();

		String emailFrom = new String();
		List<String> emailTo = new ArrayList<String>();
		List<String> emailCC = new ArrayList<String>();
		String subject = AppConstants.forgetPasswordSubject;
		String text = emailHtmlLoader.getText(AppConstants.forgetPasswordTemplate, appUrl + "resetPwd/" + jwt);

		emailTo.add(user.getEmail());

		EmailSubject emailSubject = null;
		try {
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

	public void createAdmin() {

		Optional<User> admin = null;

		try {
			admin = userRepository.findFirstAdmin();
		} catch (Exception ex) {
			logger.error("***************** Unable to find admin ********************* " + ex);
		}

		if (admin != null && !admin.isPresent()) {

			User user = new User("admin", "sysadmin", "sysadmin@gmail.com", "welcome", 'Y');

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			// Since emp id mandatory
			user.setEmployeeId("ADMIN");

			try {
				userRepository.save(user);

				// save the user manager mapping
				String role = userRole.getName();
				String role_name = "Admin";

				UserRoleEntity userManMap = new UserRoleEntity();
				userManMap.setEmpId("ADMIN");
				userManMap.setManagerId("ADMIN");
				userManMap.setDesignation("Sysetem Administrator");
				userManMap.setRole(role_name);

				userRoleRepository.save(userManMap);

			} catch (Exception ex) {
				logger.error("***************** Unable to create admin ********************* " + ex);
			}

		}

	}

//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
//
//		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//		if (errorMap != null)
//			return errorMap;
//
//		logger.info("***************** User signin *********************\n" + loginRequest.getUsernameOrEmail());
//
////		testConnection();
////		
//		createAdmin();
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		String jwt = "";
//
//		jwt = tokenProvider.generateToken(authentication);
//
//		User user = null;
//
//		Long userId = getUserIdFromJWT(jwt);
//
//		Optional<User> opt = userRepository.findUser(userId);
//
//		if (!opt.isPresent()) {
//
//			return new ResponseEntity(new ApiResponse(false, "User is not found !"), HttpStatus.BAD_REQUEST);
//		}
//
//		user = opt.get();
//
//		String name = user.getName() == null ? "" : user.getName();
//		String full_name = user.getFull_name() == null ? "" : user.getFull_name();
//
//		SCAUtil scaUtil = new SCAUtil();
//
//		if ("Y".equals(loginRequest.getIsSuperUser())) {
//
//			if (!"Y".equals(user.getIs_super_user())) {
//
//				return new ResponseEntity(new ApiResponse(false, "User is not a Super User!"), HttpStatus.BAD_REQUEST);
//			}
//
//			jwt = tokenProvider.generateCoOrdinatorToken(authentication, loginRequest.getVendorID());
//		}
//
//		JwtAuthenticationResponse resp = new JwtAuthenticationResponse(jwt);
//		resp.setName(name);
//		resp.setFull_name(full_name);
//
//		return ResponseEntity.ok(resp);
//	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
//
//		URI location = null;
//
//		try {
//
//			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//			if (errorMap != null)
//				return errorMap;
//			String getAgentOrBuyerName = signUpRequest.getName() != null ? signUpRequest.getName() : "";
//
//			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//				return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
//			}
//
//			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//				return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//						HttpStatus.BAD_REQUEST);
//			}
//
////		java.util.Date date = poAgentsOracleRepositoryOracle.findDateFromServer();
//
//			if (!"ROLE_ADMIN".equals(signUpRequest.getUserRoles())
//					&& !"ROLE_MANAGER".equals(signUpRequest.getUserRoles())
//					&& !"ROLE_SALES".equals(signUpRequest.getUserRoles())) {
//
//				if (signUpRequest.getAgentId() == null && signUpRequest.getVendorID() == null) {
//					return new ResponseEntity(new ApiResponse(false, "User must be a supplier or a buyer!"),
//							HttpStatus.BAD_REQUEST);
//				}
//
//				if (!(("ROLE_BUYER".equals(signUpRequest.getUserRoles()) && signUpRequest.getAgentId() != null)
//						|| ("ROLE_REQUESTOR".equals(signUpRequest.getUserRoles()) && signUpRequest.getAgentId() != null)
//						|| ("ROLE_SUPPLIER".equals(signUpRequest.getUserRoles())
//								&& signUpRequest.getVendorID() != null))) {
//					return new ResponseEntity(new ApiResponse(false, "Role must have a proper supplier or a buyer id!"),
//							HttpStatus.BAD_REQUEST);
//				}
//
//			}
//
//			// Creating user's account
//			User user = new User(getAgentOrBuyerName, signUpRequest.getUsername(), signUpRequest.getEmail(),
//					signUpRequest.getPassword(), signUpRequest.getOrgname(), signUpRequest.getVendorID(),
//					signUpRequest.getAgentId(), 'Y');
//
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//			user.setFull_name(signUpRequest.getFull_name());
//
//			if (signUpRequest.getVendorID() != null) {
//				user.setSupplier_flag('Y');
//				user.setBuyer_flag('N');
//			}
//
//			if (signUpRequest.getAgentId() != null) {
//				user.setSupplier_flag('N');
//				user.setBuyer_flag('Y');
//			}
//			Role userRole = roleRepository.findByName(signUpRequest.getUserRoles())
//					.orElseThrow(() -> new AppException("User Role not set."));
//
//			user.setRoles(Collections.singleton(userRole));
//
//			user.setIs_super_user("N");
//
//			if ("ROLE_SUPERUSER".equals(signUpRequest.getUserRoles())) {
//
//				user.setIs_super_user("Y");
//
//			}
//
//			User results = userRepository.save(user);
//
//			location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
//					.buildAndExpand(results.getUsername()).toUri();
//
//		} catch (Exception ex) {
//
//			logger.error("***************** Unable to register user!  *********************\n" + ex);
//
//			String msg = ex.getMessage() != null ? ex.getMessage() : "";
//
//			String cause = "";
//
//			if (ex.getCause() != null) {
//
//				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";
//
//				msg = msg + "!!!" + cause;
//			}
//
//			return new ResponseEntity(new ApiResponse(false, " Unable to register user!" + msg),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		if (loginRequest.getUsernameOremployeeId() == null) {
			return new ResponseEntity(new ApiResponse(false, "Username Or EmployeeId must not be blank"),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("***************** User signin *********************\n" + loginRequest.getUsernameOremployeeId());

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsernameOremployeeId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = "";

		jwt = tokenProvider.generateToken(authentication);

		User user = null;

		Long userId = getUserIdFromJWT(jwt);

		Optional<User> opt = userRepository.findById(userId);

		if (!opt.isPresent()) {

			return new ResponseEntity(new ApiResponse(false, "User is not found !"), HttpStatus.BAD_REQUEST);
		}

		user = opt.get();
		
		
		 if('Y' != user.getIs_Active()) {
			 return new ResponseEntity(new ApiResponse(false, "User is inactive please contact admin"),
						HttpStatus.BAD_REQUEST);
	        }
		

		// check the manager mapping validation
		Optional<UserRoleEntity> usrRoleEntity = userRoleRepository.findById(user.getEmployeeId());
		if (!usrRoleEntity.isPresent()) {
			return new ResponseEntity(new ApiResponse(false, "Please add the manager mapping to this user!"),
					HttpStatus.BAD_REQUEST);
		}
		
		

		String email = user.getEmail() == null ? "" : user.getEmail();
		String name = user.getName() == null ? "" : user.getName();
		String designation = usrRoleEntity.get().getDesignation() == null ? "" : usrRoleEntity.get().getDesignation();
		String isBulkUpload = user.getIs_bulk_upload() == null ? "" : user.getIs_bulk_upload();
		Integer dateFreeze = user.getDate_freeze() == null ? 0 : user.getDate_freeze();
		String isLaptopDetailsSubmitted = user.getIs_laptop_details_submitted() == null ? "N" : user.getIs_laptop_details_submitted();

		SCAUtil scaUtil = new SCAUtil();

		JwtAuthenticationResponse resp = new JwtAuthenticationResponse(jwt);
		resp.setName(name);
		resp.setDesignation(designation);
		resp.setEmailId(email);
		resp.setIs_bulk_upload(isBulkUpload);
		resp.setDate_freeze(dateFreeze);
		resp.setIs_laptop_details_submitted(isLaptopDetailsSubmitted);

		return ResponseEntity.ok(resp);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/updateUser")
	public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
		try {

			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
			if (errorMap != null)
				return errorMap;

			if (signUpRequest.getId() == 0) {
				return new ResponseEntity(new ApiResponse(false, "Please specify User Id to Update."),
						HttpStatus.BAD_REQUEST);
			}

			User user = userRepository.findUserByIdToActiveOrInactive(signUpRequest.getId());

			String email = user.getEmail();
			String username = user.getUsername();
			String empId = user.getEmployeeId();

			if (!(userRepository.existsById(signUpRequest.getId()))) {
				return new ResponseEntity(new ApiResponse(false, "There is no user with this ID"),
						HttpStatus.BAD_REQUEST);

			}

			if (!(empId.equals(signUpRequest.getEmployeeId()))) {
				if (userRepository.existsByEmployeeId(signUpRequest.getEmployeeId())) {
					return new ResponseEntity(new ApiResponse(false, "EmployeeID is already taken!"),
							HttpStatus.BAD_REQUEST);
				}
			}

			if (!(username.equals(signUpRequest.getUsername()))) {
				if (userRepository.existsByUsername(signUpRequest.getUsername())) {
					return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
							HttpStatus.BAD_REQUEST);
				}
			}

			if (!(email.equals(signUpRequest.getEmail()))) {
				if (userRepository.existsByEmail(signUpRequest.getEmail())) {
					return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
							HttpStatus.BAD_REQUEST);
				}
			}
			if (signUpRequest.getName() == null) {
				return new ResponseEntity(new ApiResponse(false, "Name cannot be blank!"), HttpStatus.BAD_REQUEST);
			}
			if (!(kpiAndKraGroupRepository.existsById(signUpRequest.getGroup_id()))) {
				return new ResponseEntity(new ApiResponse(false, "Group With this Id does not exist"),
						HttpStatus.BAD_REQUEST);
			}
			if (!(designationRepository.existsByDesignation(signUpRequest.getDesignation()))) {
				return new ResponseEntity(new ApiResponse(false, "Designation does not exist"), HttpStatus.BAD_REQUEST);
			}

			// User country mapping
			if (signUpRequest.getCountry_code() != null && !"".equals(signUpRequest.getCountry_code())) {

				assignLeaveCategoryToUser(signUpRequest.getCountry_code(), user.getId());
			}
			// End - User country mapping

			userService.updateUser(signUpRequest);

		} catch (Exception ex) {

			logger.error("***************** Unable to update user!  *********************\n" + ex);

			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}

			return new ResponseEntity(new ApiResponse(false, " Unable to Update user!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, "User has been updated successfully "), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {

		createAdmin();

		URI location = null;

		try {

			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
			if (errorMap != null)
				return errorMap;

			if (userRepository.existsByEmployeeId(signUpRequest.getEmployeeId())) {
				return new ResponseEntity(new ApiResponse(false, "EmployeeID is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
						HttpStatus.BAD_REQUEST);
			}
			if (signUpRequest.getName() == null) {
				return new ResponseEntity(new ApiResponse(false, "Name cannot be blank!"), HttpStatus.BAD_REQUEST);
			}

			if (!(kpiAndKraGroupRepository.existsById(signUpRequest.getGroup_id()))) {
				return new ResponseEntity(new ApiResponse(false, "Group With this Id does not exist"),
						HttpStatus.BAD_REQUEST);
			}
			if (!(designationRepository.existsByDesignation(signUpRequest.getDesignation()))) {
				return new ResponseEntity(new ApiResponse(false, "Designation does not exist"), HttpStatus.BAD_REQUEST);
			}

			String password = signUpRequest.getPassword();
			// Creating user's account
			User user = new User(signUpRequest.getEmployeeId(), signUpRequest.getName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), 'Y', signUpRequest.getAvatarUrl());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// String groups[] = new String[] {"Default Group"};

			Role userRole = roleRepository.findByName(signUpRequest.getUserRoles())
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			// User country mapping
			if (signUpRequest.getCountry_code() != null && !"".equals(signUpRequest.getCountry_code())) {

				assignLeaveCategoryToUser(signUpRequest.getCountry_code(), user.getId());
				user.setCountry_code(signUpRequest.getCountry_code());
			}

			// bulk_upload and date freeze
			if (signUpRequest.getIs_bulk_upload() == null) {
				user.setIs_bulk_upload("N");
			} else {
				user.setIs_bulk_upload(signUpRequest.getIs_bulk_upload());
			}

			if (signUpRequest.getDate_freeze() == null) {
				user.setDate_freeze(0);
			} else {
				user.setDate_freeze(signUpRequest.getDate_freeze());
			}

			// End - User country mapping
			
			// save laptop details submit status
			user.setIs_laptop_details_submitted("N");

			User results = userRepository.save(user);

			// save the user manager mapping
			String role = userRole.getName();
			String role_name = "Team Member";

			if ("ROLE_MANAGER".equals(role)) {

				role_name = "Manager";
			}
			
			String employeeId = signUpRequest.getEmployeeId();
			String managerId = signUpRequest.getManagerId();

			UserRoleEntity userManMap = new UserRoleEntity();
			userManMap.setEmpId(signUpRequest.getEmployeeId());
			userManMap.setDesignation(signUpRequest.getDesignation());
			userManMap.setRole(role_name);
			userManMap.setGroupId(signUpRequest.getGroup_id());
			if(employeeId.equals(managerId)) {
				throw new BadRequestException("Same User Cannot be a manager for himself " +employeeId );
			} else {
				userManMap.setManagerId(signUpRequest.getManagerId());
			}

			userRoleRepository.save(userManMap);

			String emailFrom = new String();
			List<String> emailTo = new ArrayList<String>();
			List<String> emailCC = new ArrayList<String>();
			String subject = AppConstants.signInDetailsSubject;

			String text = emailHtmlLoader.sendSignInContent(user, password);
			emailTo.add(user.getEmail());

			EmailSubject emailSubject = null;
			try {
				emailSubject = EmailSubject.getInstance(emailDetailsRepository);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidKeySpecException e) {

				throw new AppException("Unable to get Email details");
			}

			String fromEmail = emailSubject.getUsername();
			emailFrom = fromEmail;

			emailSubject.init(emailFrom, emailTo, emailCC, null, subject, text);
			emailSubject.setHTML(true);

			SendMail sm = new SendMail();

			sm.sendMail(emailSubject);

			location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
					.buildAndExpand(results.getUsername()).toUri();

		} catch (Exception ex) {

			logger.error("***************** Unable to register user!  *********************\n" + ex);

			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}

			return new ResponseEntity(new ApiResponse(false, " Unable to register user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

	private Long getUserIdFromJWT(String jwt) {

		Long userId = tokenProvider.getUserIdFromJWT(jwt);

		return userId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/download/samplefile")
	public ResponseEntity<?> downloadSampleExcel() throws AppException, ExcelException, Exception {

		String SHEET = "UserUploadTemplate";

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Workbook workbook = new XSSFWorkbook();

		InputStreamResource file = null;
		String filename = "UserUploadTemplate";
		String extension = ".xlsx";

		filename = filename + extension;

		try {

			Sheet sheet = workbook.createSheet(SHEET);

			userService.generateSampleFile(workbook, sheet);

			workbook.write(out);

			file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

		} catch (Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;

			}
			return new ResponseEntity(new ApiResponse(false, "Failed to Generate Template File: " + msg),
					HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);

	}

	@Value("${file.upload-dir}")
	String FILE_DIRECTORY;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/fileUpload")
	public ResponseEntity<?> uploadExcel(@RequestParam("File") MultipartFile file) throws IOException {

		try {
			File uploadedFile = new File(FILE_DIRECTORY + "UserUploadTemplate.xlsx");

			uploadedFile.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(uploadedFile);
			fileOut.write(file.getBytes());
			fileOut.close();

			userService.readExcelData(uploadedFile);

		} catch (Exception ex) {

			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, "File Uploading Failed!  " + msg), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "File Uploaded Successfully"), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("/updateNewPasswordForUser")
	public ResponseEntity<?> updateNewPasswordForUSer(HttpServletRequest httpServletRequest,
			@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

		Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest, tokenProvider);
		return userService.updatePasseord(userIdFromRequest, loginRequest.getPassword());

	}

	public ResponseEntity<?> assignLeaveCategoryToUser(String countryCode, Long userId) {

		List<LeaveBalanceEntity> leaveBalanceList = new ArrayList<LeaveBalanceEntity>();
		SCAUtil sca = new SCAUtil();

		try {

			Date date = new Date();
			String year = String.valueOf(date.getYear() + 1900);

			List<LeaveMasterEntity> leaveMasters = leaveMasterRepository.findLeaveMasterByCountry(countryCode);

			if (leaveMasters.isEmpty()) {

				return new ResponseEntity(new ApiResponse(false, "No Leave Master for this country : " + countryCode),
						HttpStatus.BAD_REQUEST);
			}

			for (LeaveMasterEntity leave : leaveMasters) {

				LeaveBalanceEntity leaveBalance = new LeaveBalanceEntity();
				leaveBalance.setUser_id(userId);
				leaveBalance.setCountry_code(leave.getCountry_code());
				leaveBalance.setCategory_name(leave.getCategory_name());
				leaveBalance.setBalance_leave(leave.getTotal_leave());
				leaveBalance.setLeave_master_id(leave.getLeave_master_id());
				leaveBalance.setYear(year);
				leaveBalance.setTotal_leave(leave.getTotal_leave());

				leaveBalanceList.add(leaveBalance);
			}

			leaveBalanceRepository.saveAll(leaveBalanceList);

		} catch (Exception e) {

			logger.error("***************** Unable to assign leave to user!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to assign leave to user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(leaveBalanceList, HttpStatus.CREATED);
	}

}
