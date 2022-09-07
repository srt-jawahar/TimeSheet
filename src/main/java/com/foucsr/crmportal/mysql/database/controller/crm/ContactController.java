package com.foucsr.crmportal.mysql.database.controller.crm;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.crm.LeadsRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.crm.LeadsService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateContacts;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.SCAUtil;

@RestController
@RequestMapping("/api/Contact/Service")
public class ContactController {

	
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
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/CreateContact")
	public ResponseEntity<?> createContacts(@Valid @RequestBody CreateOrUpdateContacts createLeadRequest,BindingResult result, Principal principal) {

		
		try {
			if(!(userRepository.existsByUsername(createLeadRequest.getContactOwner()))) {
				return new ResponseEntity<>(new ApiResponse(false, "Lead Owner with this Username  does not exist!"),HttpStatus.BAD_REQUEST);
				
			}
			User newuser = userRepository.findByUsername(createLeadRequest.getContactOwner()).orElseThrow(
					() -> new ResourceNotFoundException("Lead Owner with this Username  does not exist!", "id", createLeadRequest.getContactOwner()));
			
			  Set<Role> Roles = newuser.getRoles();
			  for (Role role : Roles) {
				  
				  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
					  
				  }else {
						return new ResponseEntity<>(new ApiResponse(false, "Lead Owner mentioned here is Not a valid Lead Owner"),HttpStatus.BAD_REQUEST);
				  }
				
			}
             if(leadsRepository.existsByEmail(createLeadRequest.getEmail())) {
				
	    		return new ResponseEntity(new ApiResponse(false,"Email is already in use"),HttpStatus.BAD_REQUEST);   
					
					}
		
		  leadsService.createContacts(createLeadRequest);
		
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
				
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to create Contact!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Contact has been Created successfully "), HttpStatus.CREATED);
		}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/UpdateContact")
	public ResponseEntity<?> UpdateContacts(@Valid @RequestBody CreateOrUpdateContacts createLeadRequest,BindingResult result, Principal principal) {

		try {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		if(!(userRepository.existsByUsername(createLeadRequest.getContactOwner()))) {
			return new ResponseEntity<>(new ApiResponse(false, "Contact Owner with this Username  does not exist!"),HttpStatus.BAD_REQUEST);
			
		}
		User newuser = userRepository.findByUsername(createLeadRequest.getContactOwner()).orElseThrow(
				() -> new ResourceNotFoundException("Contact Owner with this Username  does not exist!", "id", createLeadRequest.getContactOwner()));
		
		  Set<Role> Roles = newuser.getRoles();
		  for (Role role : Roles) {
			  
			  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
				  
			  }else {
					return new ResponseEntity<>(new ApiResponse(false, "Contact Owner mentioned here is Not a valid Contact Owner"),HttpStatus.BAD_REQUEST);
			  }
			
		}
		if(!(leadsRepository.existsById(createLeadRequest.getLeadId()))) {
			return new ResponseEntity(new ApiResponse(false, "Contact with the ID : "+createLeadRequest.getLeadId()+" does not exist"),HttpStatus.BAD_REQUEST);
		}
		 Leads leadsCheck = leadsRepository.findById(createLeadRequest.getLeadId()).orElseThrow(
					() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", createLeadRequest.getLeadId()));
		 String email = leadsCheck.getEmail();
			if(!(email.equals(createLeadRequest.getEmail()))) {

		if(leadsRepository.existsByEmail(createLeadRequest.getEmail())) {
			    		return new ResponseEntity(new ApiResponse(false,"Email is already in use"),HttpStatus.BAD_REQUEST);
		}
			}
		
		 leadsService.updateContacts(createLeadRequest);
		
		
		
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Update Contact!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Contact has been Updated successfully "), HttpStatus.OK);
		}
	
	
	@GetMapping("/getListOfContacts")
	public List<Leads> getAllContacts(HttpServletRequest httpServletRequest,Principal principal) {
		
		 Long userIdFromRequest = scaUtil.getUserIdFromRequest(httpServletRequest,tokenProvider);
		 User userCheck = userRepository.findUserByIdToActiveOrInactive(userIdFromRequest);
		 List<Leads> listofContacts = null;
		 Set<Role> Roles = userCheck.getRoles();
		  for (Role role : Roles) {
			  
			  if(role.getName().contains("ROLE_SALES")){
					 listofContacts = leadsRepository.findListOfLeadsForSales("CONTACT",userCheck.getUsername()); 
			  }
		 else {
		 listofContacts = leadsRepository.findListOfLeads("CONTACT");
		 }
		  
		  }
		return listofContacts;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/MultipleChangeContactOwner")
	public ResponseEntity<?> multipleChangeContactOwner(@RequestBody MultipleSelectRequest multipleSelectRequest){
		try{
			Long ids[] = multipleSelectRequest.getIds();
			
			if(!(userRepository.existsByUsername(multipleSelectRequest.getLeadOwner()))) {
				return new ResponseEntity<>(new ApiResponse(false, "Contact Owner with this Username  does not exist!"),HttpStatus.BAD_REQUEST);
				
			}
			User newuser = userRepository.findByUsername(multipleSelectRequest.getLeadOwner()).orElseThrow(
					() -> new ResourceNotFoundException("Contact Owner with this Username  does not exist!", "id", multipleSelectRequest.getLeadOwner()));
			
			  Set<Role> Roles = newuser.getRoles();
			  for (Role role : Roles) {
				  
				  if((role.getName().contains("ROLE_SALES"))||(role.getName().contains("ROLE_MANAGER"))){
					  
				  }else {
						return new ResponseEntity<>(new ApiResponse(false, "Contact Owner mentioned here is Not a valid Contact Owner"),HttpStatus.BAD_REQUEST);
				  }
				
			}
		
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(leadsRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "Contact does not exist."),HttpStatus.BAD_REQUEST);
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
    		return new ResponseEntity(new ApiResponse(false, "Could not change Contact Owner  " + msg),
    				HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity(new ApiResponse(true, "Contact Owner has been changed successfully"),HttpStatus.OK);

    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/getSingleContact/{id}")
     public ResponseEntity<?> getContact(@PathVariable long id, Principal principal)throws AppException {
		
			
			if(!(leadsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Contact with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			Leads newLead = leadsService.getLeadOrContact(id);
			
			
		return  ResponseEntity.status(HttpStatus.OK).body(newLead);   
}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/MultipleDeleteContacts")
	public ResponseEntity<?> multipleDeleteContacts(@RequestBody MultipleSelectRequest multipleSelectRequest){
		try{
			Long ids[] = multipleSelectRequest.getIds();
		
    	for(int i=0;i<ids.length;i++) {
    		
    		if(!(leadsRepository.existsById(ids[i]))) {
    			
    			return new ResponseEntity(new ApiResponse(false, "Contact does not exist."),HttpStatus.BAD_REQUEST);
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
    		return new ResponseEntity(new ApiResponse(false, "Could not delete Contacts  " + msg),
    				HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity(new ApiResponse(true, "Contacts is now Deleted"),HttpStatus.OK);

    }

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/deleteContact/{id}")
     public ResponseEntity<?> deleteContact(@PathVariable long id, Principal principal)throws AppException {
		try {
			
			if(!(leadsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Contact with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			leadsService.deleteLeadById(id);
			
			
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, "Could not delete Contact  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	
	return new ResponseEntity(new ApiResponse(true, "Contact in now deleted"),HttpStatus.OK);
}
}
