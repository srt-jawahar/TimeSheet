package com.foucsr.crmportal.mysql.database.controller.crm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.foucsr.crmportal.mysql.database.model.crm.Accounts;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.repository.crm.AccountsRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.crm.AccountsService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.crm.CreateAccountRequest;

@RestController
@RequestMapping("/api/accounts/services")
public class AccountsController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private AccountsService accountsService;
	
	@GetMapping("/getAllAccounts")
	public List<Accounts> getAllAccounts(Principal principal) {
		
		List<Accounts> AccsList = accountsRepository.findAll();
		return AccsList;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest,
			BindingResult result, Principal principal) {
		try {
			
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		accountsService.createAccount(createAccountRequest);
		
	    }  catch(Exception ex) {
	    	
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to create Account!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
		
		return new ResponseEntity(new ApiResponse(true, "Account Created Successfully!") , HttpStatus.CREATED);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/update")
	public ResponseEntity<?> updateAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest,
			BindingResult result, Principal principal) {
		
		try {
			
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		
		if(!(accountsRepository.existsById(createAccountRequest.getAccountId()))) {
			
			return new ResponseEntity(new ApiResponse(false, "Account with the ID : "+createAccountRequest.getAccountId()+" does not exist"),HttpStatus.BAD_REQUEST);
			
		}
		
		accountsService.updateAccount(createAccountRequest);
		
		} catch(Exception ex) {
			
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Update Account!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity(new ApiResponse(true, "Account Updated Successfully!") , HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteAccountById(@PathVariable long id, Principal principal)throws AppException {
		try {
			
			if(!(accountsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Account with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			accountsService.deleteAccountById(id);	
			
	    } catch(Exception ex) {
	    	
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, "Could not delete the Account  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	
	return new ResponseEntity(new ApiResponse(true, "Account is now deleted"),HttpStatus.OK);
}
	
	@GetMapping("/AccountTypes")
	public List<String> getAccountTypes() {
		
		List<String> accTypes = new ArrayList<>();
		
		accTypes.add("Anaslyst");
		accTypes.add("Competitor");
		accTypes.add("Customer");
		accTypes.add("Distributor");
		accTypes.add("Integrator");
		accTypes.add("Investor");
		accTypes.add("Partner");
		accTypes.add("Press");
		accTypes.add("Prospect");
		accTypes.add("Reseller");
		accTypes.add("Supplier");
		accTypes.add("Vendor");
		accTypes.add("Other");
		
		return accTypes;
	}
	
	@GetMapping("/LeadSources")
	public List<String> getLeadSource() {
		
		List<String> listOfLeadSources = new ArrayList<>();
	
		listOfLeadSources.add("Advertisement");
		listOfLeadSources.add("Cold Call");
		listOfLeadSources.add("Employee Referral");
		listOfLeadSources.add("External Referral");
		listOfLeadSources.add("Online Store");
		listOfLeadSources.add("Partner");
		listOfLeadSources.add("Public Relations");
		listOfLeadSources.add("Sales Email Alias");
		listOfLeadSources.add("Seminar Partner");
		listOfLeadSources.add("Internal Seminar");
		listOfLeadSources.add("Trade Show");
		listOfLeadSources.add("Web Download");
		listOfLeadSources.add("Web Research");
		listOfLeadSources.add("Chat");
		
		return listOfLeadSources;
	}
	
	@GetMapping("/Industries")
	public List<String> getIndustries() {
		
		List<String> industries = new ArrayList<>();
	
		industries.add("ASP(Application Service Provider)");
		industries.add("Data/Telecom OEM");
		industries.add("ERP(Enterprise Resource Planning)");
		industries.add("Governmnet/Military");
		industries.add("Large Enterprise");
		industries.add("Management ISV");
		industries.add("MSP (Management Service Provider)");
		industries.add("Network Equipment Enterprise");
		industries.add("Non-Management ISV");
		industries.add("Optical Networking");
		industries.add("Service Provider");
		industries.add("Small/Medium Enterprise");
		industries.add("Storage Equipment");
		industries.add("Storage Service Provider");
		industries.add("Systems Integrator");
		industries.add("Wireless Industry");
		industries.add("Communications");
		industries.add("Consulting");
		industries.add("Education");
		industries.add("Financial Services");
		industries.add("Manufacturing");
		industries.add("Real Esatate");
		industries.add("Technology");
		
		return industries;
	}
	
	@GetMapping("/OwnershipTypes")
	public List<String> getOwnershipTypes() {
		
		List<String> ownership = new ArrayList<>();
		
		ownership.add("Private");
		ownership.add("Public");
		ownership.add("Subsidiary");
		ownership.add("Government");
		ownership.add("Partnership");
		ownership.add("Privately Held");
		ownership.add("Public Company");
		
		return ownership;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/getAccount/{id}")
     public ResponseEntity<?> getAccountById(@PathVariable long id, Principal principal)throws AppException {
		
			
			if(!(accountsRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Account with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
			
			Accounts accs = accountsService.getAccountById(id);
			
			
		return  ResponseEntity.status(HttpStatus.OK).body(accs);   
}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/DeleteMultipleAccounts")
	public ResponseEntity<?> multipleDeleteAccounts(@RequestBody MultipleSelectRequest multipleSelectRequest){
		
	try {
		
	Long ids[] = multipleSelectRequest.getIds();

	for(int i=0;i<ids.length;i++) {

	if(!(accountsRepository.existsById(ids[i]))) {

	return new ResponseEntity(new ApiResponse(false, "Account does not exist."),HttpStatus.BAD_REQUEST);
	
	}

	accountsService.deleteAccountById(ids[i]);
	
	}
	
	} catch (Exception ex) {
		
	String msg = ex.getMessage() != null ? ex.getMessage() : "";

	String cause = "";

	if (ex.getCause() != null) {

	cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

	msg = msg + "!!!" + cause;
	
	}
	
	return new ResponseEntity(new ApiResponse(false, "Could not delete selected accounts " + msg),
			
	HttpStatus.BAD_REQUEST);
	
	}
	
	return new ResponseEntity(new ApiResponse(true, "accounts are now Deleted"),HttpStatus.OK);

	}
	
}
	

