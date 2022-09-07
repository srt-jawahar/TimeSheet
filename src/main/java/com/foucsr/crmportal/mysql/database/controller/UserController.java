package com.foucsr.crmportal.mysql.database.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ExcelException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.Designation;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.crm.Accounts;
import com.foucsr.crmportal.mysql.database.repository.DesignationRepository;
import com.foucsr.crmportal.mysql.database.repository.RoleRepository;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.mysql.database.service.crm.DealsService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.DeleteUserRequest;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.UpdateUserRequest;
import com.foucsr.crmportal.payload.UserProfile;
import com.foucsr.crmportal.payload.UserSummary;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateDealsRequest;
import com.foucsr.crmportal.security.CurrentUser;
import com.foucsr.crmportal.security.JwtAuthenticationFilter;
import com.foucsr.crmportal.security.UserPrincipal;

@RestController
@RequestMapping("/api/Users/Service")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	DesignationRepository designationRepository;

	
	// private static final Logger logger =
	// LoggerFactory.getLogger(UserController.class);

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}
	

	@GetMapping("/users/{userid}")
	public UserProfile getUserProfile(@PathVariable(value = "userid") long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		 if('Y' != user.getIs_Active()) {
	        	throw new UsernameNotFoundException("User is not active : " + user);
	     }
		
		

		UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getEmployeeId(),
				user.getEmail(),user.getAvatarUrl());

		return userProfile;
	}

	@GetMapping("/getListOfUsers")
	public List<UserProfile> getAllProjects(Principal principal) {
		List<UserProfile> listofUsers = userService.findAllProjects();
		return listofUsers;
	}
	
	@GetMapping("/getListOfRoles")
	public List<Role> getAllRoles(Principal principal) {
		List<Role> listofUsers = roleRepository.findAll();
		return listofUsers;
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PutMapping("/updateUserDetails")
//	public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UpdateUserRequest signUpRequest,
//			BindingResult result) {
//		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//		if (errorMap != null)
//			return errorMap;
//
//		User user = userRepository.findById(signUpRequest.getId()).orElseThrow(
//				() -> new ResourceNotFoundException("User Id is does not exist!", "id", signUpRequest.getId()));
//
//		if (signUpRequest.getPassword() != null) {
//			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//		}
//		
//		user.setFull_name(signUpRequest.getFull_name());
//		
//		if (signUpRequest.getUserRoles() != null) {
//		Role userRole = roleRepository.findByName(signUpRequest.getUserRoles())
//				.orElseThrow(() -> new AppException("User Role not set."));
//			Set rolesMap = new HashSet();
//			Role setRoles = new Role();
//			setRoles.setId((long) userRole.getId());
//			setRoles.setName(userRole.getName());
//			user.setRoles(rolesMap);
//
//			Set rolesMap1 = new HashSet();
//			rolesMap1.add(userRole);
//			user.setRoles(rolesMap1);
//		}
//		if (signUpRequest.getName() != null) {
//			user.setName(signUpRequest.getName());
//		}
//		
//		/*if(signUpRequest.getUsername()!=null) {
//			user.setUsername(signUpRequest.getUsername());
//			}*/
//		
//		if(signUpRequest.getOrgname()!=null) {
//			user.setOrgname(signUpRequest.getOrgname());
//		}
//		user.setEmail(signUpRequest.getEmail());
//		User results = userRepository.save(user);
//
//		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
//				.buildAndExpand(results.getUsername()).toUri();
//
//		return ResponseEntity.created(location).body(new ApiResponse(true, "User Updated successfully"));
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable long userId, Principal principal) {
		
		try {
		     if(!(userRepository.existsById(userId))) {
					
					return new ResponseEntity(new ApiResponse(false, "User does not exist."),HttpStatus.BAD_REQUEST);
				}
		     
		     
				userRepository.deleteByIdInMap(userId);

		userService.deleteUserById(userId);
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Delete User  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "User  deleted successfully"),HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/deleteMultipleUser")
	public ResponseEntity<?> multipleDeleteLeads(@RequestBody MultipleSelectRequest multipleSelectRequest){
		try{
			Long ids[] = multipleSelectRequest.getIds();
		
    	for(int i=0;i<ids.length;i++) {
    		
    		
		     if(!(userRepository.existsById(ids[i]))) {
					
					return new ResponseEntity(new ApiResponse(false, "User does not exist."),HttpStatus.BAD_REQUEST);
				}
		     
		     
				userRepository.deleteByIdInMap(ids[i]);

		userService.deleteUserById(ids[i]);
    	}
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Delete User " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "User  deleted successfully"),HttpStatus.OK);
	}
	
	@PostMapping("/activeOrInactive")
	public ResponseEntity<?> activeOrInactiveUser(@Valid @RequestBody DeleteUserRequest deleteRequest,
			Principal principal) {
		return userService.activeOrInactiveUser(deleteRequest);

	}
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers(Principal principal) {
		
		List<User> users = userRepository.findAll();
		return users;
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/Multiple/SetAsActive")
	public ResponseEntity<?> setAsActiveMultipleCandidates(@RequestBody MultipleSelectRequest multipleSelectRequest){
		Long ids[] = multipleSelectRequest.getIds();
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(userRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "User does not exist."),HttpStatus.BAD_REQUEST);
    		}
         
            
    		userService.setasActive(ids[i]);

    	}
		return new ResponseEntity(new ApiResponse(true, "Selected User(s) are now Active"),HttpStatus.OK);

    		
    	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/Multiple/SetAsInactive")
	public ResponseEntity<?> setAsInActiveMultipleCandidates(@RequestBody MultipleSelectRequest multipleSelectRequest){
		Long ids[] = multipleSelectRequest.getIds();
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(userRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "User does not exist."),HttpStatus.BAD_REQUEST);
    		}
         
            
    		userService.setasInActive(ids[i]);

    	}
		return new ResponseEntity(new ApiResponse(true, "Selected User(s) are now Inactive"),HttpStatus.OK);

    }
	
	@GetMapping("/getListOfDesignation")
	public List<Designation> getAllDesignation(Principal principal) {
		List<Designation> listofDesignation = designationRepository.findAll();
		return listofDesignation;
	}
	
}
