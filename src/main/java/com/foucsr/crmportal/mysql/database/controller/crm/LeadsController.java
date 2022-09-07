package com.foucsr.crmportal.mysql.database.controller.crm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.crm.FilesInfo;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.crm.LeadsRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.crm.LeadsService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.crm.ConvertLeadToContactRequest;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateContacts;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateLeadRequest;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.SCAUtil;

@RestController
@RequestMapping("/api/Leads/Service")
public class LeadsController {
	
	
	
	
	@Autowired
	private LeadsRepository leadsRepository;
	
	@Autowired
	private LeadsService leadsService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
    @Autowired
    SCAUtil scaUtil;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/CreateLead")
	public ResponseEntity<?> createLeads(@Valid @RequestBody CreateOrUpdateLeadRequest createLeadRequest,BindingResult result, Principal principal) {

		
		try {
			if(!(userRepository.existsByUsername(createLeadRequest.getLeadOwner()))) {
				return new ResponseEntity<>(new ApiResponse(false, "Lead owner does not exists"),HttpStatus.BAD_REQUEST);
				
			}
			User newuser = userRepository.findByUsername(createLeadRequest.getLeadOwner()).orElseThrow(
					() -> new ResourceNotFoundException("Lead Owner with this Username  does not exist!", "id", createLeadRequest.getLeadOwner()));
			
			  Set<Role> Roles = newuser.getRoles();
			  for (Role role : Roles) {
				  
				  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
					  
				  }else {
						return new ResponseEntity<>(new ApiResponse(false, "Lead Owner mentioned here is Not a valid Lead OWner"),HttpStatus.BAD_REQUEST);
				  }
				
			}
			  

			if(leadsRepository.existsByEmail(createLeadRequest.getEmail())) {
				
	    		return new ResponseEntity(new ApiResponse(false,"Email is already in use"),HttpStatus.BAD_REQUEST);   
					
					}
			
		
		 leadsService.createLeads(createLeadRequest);
		
		 		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to create Lead!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Lead has been Created successfully "), HttpStatus.CREATED);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/UpdateLead")
	public ResponseEntity<?> UpdateLeads(@Valid @RequestBody CreateOrUpdateLeadRequest createLeadRequest,BindingResult result, Principal principal) {

		
		try {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		if(!(userRepository.existsByUsername(createLeadRequest.getLeadOwner()))) {
			return new ResponseEntity<>(new ApiResponse(false, "Lead owner does not exists"),HttpStatus.BAD_REQUEST);
			
		}
		User newuser = userRepository.findByUsername(createLeadRequest.getLeadOwner()).orElseThrow(
				() -> new ResourceNotFoundException("Lead Owner with this Username  does not exist!", "id", createLeadRequest.getLeadOwner()));
		
		  Set<Role> Roles = newuser.getRoles();
		  for (Role role : Roles) {
			  
			  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
				  
			  }else {
					return new ResponseEntity<>(new ApiResponse(false, "Lead Owner mentioned here is Not a valid Lead OWner"),HttpStatus.BAD_REQUEST);
			  }
			
		}
		if(!(leadsRepository.existsById(createLeadRequest.getLeadId()))) {
			
			return new ResponseEntity(new ApiResponse(false, "Lead with the ID : "+createLeadRequest.getLeadId()+" does not exist"),HttpStatus.BAD_REQUEST);

			
		}
		 Leads leadsCheck = leadsRepository.findById(createLeadRequest.getLeadId()).orElseThrow(
					() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", createLeadRequest.getLeadId()));
		 String email = leadsCheck.getEmail();
			if(!(email.equals(createLeadRequest.getEmail()))) {
		if(leadsRepository.existsByEmail(createLeadRequest.getEmail())) {
			
    		return new ResponseEntity(new ApiResponse(false,"Email is already in use"),HttpStatus.BAD_REQUEST);   
				
				}
			}
		
		leadsService.updateLeads(createLeadRequest);
		
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Update Lead!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Lead has been Updated successfully "), HttpStatus.OK);
		}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/ConvertToContact")
	public ResponseEntity<?> convertToContacts(@Valid @RequestBody ConvertLeadToContactRequest createLeadRequest,BindingResult result, Principal principal) {

		try {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		
		 leadsService.convertContacts(createLeadRequest);
		
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

			   	cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to convert Lead to Contact!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Lead has been converted into Contact successfully "), HttpStatus.OK);
		}
	

	
	
	@GetMapping("/getListOfLeadOwner")
	public List<String> getListOfLeadOwner(HttpServletRequest httpServletRequest,Principal principal) {
		
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 User userCheck = userRepository.findUserByIdToActiveOrInactive(userIdFromRequest);
		 List<String> listofLeads = new ArrayList<>();
		  Set<Role> Roles = userCheck.getRoles();
		  for (Role role : Roles) {
			  
			  if(role.getName().contains("ROLE_SALES")){
				  String username = userCheck.getUsername();
					 listofLeads.add(username);
			  }
		 else {
			 listofLeads = userRepository.findListOfLeadOwner();
		 }
		  
		  }
		return listofLeads;
	}
	
	@GetMapping("/getListOfLeads")
	public List<Leads> getAllLeads(HttpServletRequest httpServletRequest,Principal principal) {
		
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 User userCheck = userRepository.findUserByIdToActiveOrInactive(userIdFromRequest);
		 List<Leads> listofLeads = null;
		  Set<Role> Roles = userCheck.getRoles();
		  for (Role role : Roles) {
			  
			  if(role.getName().contains("ROLE_SALES")){
					 listofLeads = leadsRepository.findListOfLeadsForSales("LEAD",userCheck.getUsername()); 
			  }
		 else {
		 listofLeads = leadsRepository.findListOfLeads("LEAD");
		 }
		  
		  }
		return listofLeads;
	}
		
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/getSingleLead/{id}")
     public ResponseEntity<?> getLeadorContact(@PathVariable long id, Principal principal)throws AppException {
		
			
			if(!(leadsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Lead with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			Leads newLead = leadsService.getLeadOrContact(id);
			
			
		return  ResponseEntity.status(HttpStatus.OK).body(newLead);   
}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/MultipleChangeLeadOwner")
	public ResponseEntity<?> multipleChangeLeadOwner(@RequestBody MultipleSelectRequest multipleSelectRequest){
		try{
			Long ids[] = multipleSelectRequest.getIds();
			
			if(!(userRepository.existsByUsername(multipleSelectRequest.getLeadOwner()))) {
				return new ResponseEntity<>(new ApiResponse(false, "Lead Owner with this Username  does not exist!"),HttpStatus.BAD_REQUEST);
				
			}
			User newuser = userRepository.findByUsername(multipleSelectRequest.getLeadOwner()).orElseThrow(
					() -> new ResourceNotFoundException("Lead Owner with this Username  does not exist!", "id", multipleSelectRequest.getLeadOwner()));
			
			  Set<Role> Roles = newuser.getRoles();
			  for (Role role : Roles) {
				  
				  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
					  
				  }else {
						return new ResponseEntity<>(new ApiResponse(false, "Lead Owner mentioned here is Not a valid Lead Owner"),HttpStatus.BAD_REQUEST);
				  }
				
			}
		
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(leadsRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "Lead does not exist."),HttpStatus.BAD_REQUEST);
    		}
        
			Leads newLead = leadsRepository.findById(ids[i]).orElseThrow(
					() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", multipleSelectRequest.getIds()));
			newLead.setLeadOwner(multipleSelectRequest.getLeadOwner());
			leadsRepository.save(newLead);
			
    	}
		}catch(Exception ex) {
    		String msg = ex.getMessage() != null ? ex.getMessage() : "";

    		String cause = "";

    		if (ex.getCause() != null) {

    			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

    			msg = msg + "!!!" + cause;
    		}
    		return new ResponseEntity(new ApiResponse(false, "Could not change Lead Owner  " + msg),
    				HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity(new ApiResponse(true, "Lead Owner has been changed successfully"),HttpStatus.OK);

    }
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/MultipleDeleteLeads")
	public ResponseEntity<?> multipleDeleteLeads(@RequestBody MultipleSelectRequest multipleSelectRequest){
		try{
			Long ids[] = multipleSelectRequest.getIds();
		
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(leadsRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "Lead does not exist."),HttpStatus.BAD_REQUEST);
    		}
         
            
    		leadsService.deleteLeadById(ids[i]);
    	}
		}catch(Exception ex) {
    		String msg = ex.getMessage() != null ? ex.getMessage() : "";

    		String cause = "";

    		if (ex.getCause() != null) {

    			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

    			msg = msg + "!!!" + cause;
    		}
    		return new ResponseEntity(new ApiResponse(false, "Could not delete Leads  " + msg),
    				HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity(new ApiResponse(true, "Leads is now Deleted"),HttpStatus.OK);

    }
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/deleteLead/{id}")
     public ResponseEntity<?> deleteLead(@PathVariable long id, Principal principal)throws AppException {
		try {
			
			if(!(leadsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Lead with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			leadsService.deleteLeadById(id);
			
			
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, "Could not delete lead  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	
	return new ResponseEntity(new ApiResponse(true, "Lead in now deleted"),HttpStatus.OK);
}
	
	
	 @Value("${file.upload-dir}")
	 String FILE_DIRECTORY;
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @PostMapping("/upload/file/{id}")
	 public ResponseEntity<?> uploadFile(HttpServletRequest httpServletRequest,@PathVariable long id,@RequestParam("File") MultipartFile file) throws IOException{
		
		 
		 try {
		 File uploadedFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
		
		 uploadedFile.createNewFile();

		 
		 
		 FileOutputStream fileOut = new FileOutputStream(uploadedFile);
		 fileOut.write(file.getBytes());
		 fileOut.close();
		 
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 User newUser = userRepository.findUserByIdToActiveOrInactive(userIdFromRequest);
		 

		 FilesInfo newFile = new FilesInfo();
		 newFile.setFileName(file.getOriginalFilename());
		 newFile.setSize(file.getSize());
		 newFile.setFileOrUrl("FILE");
		 newFile.setAttachedBy(newUser.getUsername());
		 
		 leadsService.addFileInfo(id,newFile);
		 
		 
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

	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @PostMapping("/upload/url/{id}")
	 public ResponseEntity<?> uploadUrl(HttpServletRequest httpServletRequest,@PathVariable long id,@Valid @RequestBody FilesInfo filesInfo) throws IOException{
		 
		 
	 try {
		 
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 User newUser = userRepository.findUserByIdToActiveOrInactive(userIdFromRequest);
		 
		 
		 FilesInfo newFile = new FilesInfo();
		 newFile.setFileName(filesInfo.getFileName());

		 newFile.setFileOrUrl("URL");
		 newFile.setAttachedBy(newUser.getUsername());
		 newFile.setUrl(filesInfo.getUrl());
		 
		 leadsService.addFileInfo(id,newFile);
		 
	 }catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, "URL Uploading Failed!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "URL Uploaded Successfully"), HttpStatus.CREATED);
	 
	 }
	

}
