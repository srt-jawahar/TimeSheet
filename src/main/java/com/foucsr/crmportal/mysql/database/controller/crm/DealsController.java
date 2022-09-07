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
import com.foucsr.crmportal.mysql.database.model.crm.Deals;
import com.foucsr.crmportal.mysql.database.model.crm.DealsHistory;
import com.foucsr.crmportal.mysql.database.repository.crm.DealsHistoryRepository;
import com.foucsr.crmportal.mysql.database.repository.crm.DealsRepository;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.crm.DealsService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateDealsRequest;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateLeadRequest;



@RestController
@RequestMapping("/api/Deals/Service")
public class DealsController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private DealsService dealsService;
	
	@Autowired
	private DealsRepository dealsRepository;
	
	@Autowired
	private DealsHistoryRepository dealsHistoryRepository;

	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@PostMapping("/CreateDeals")
	public ResponseEntity<?> createDeals(@Valid @RequestBody CreateOrUpdateDealsRequest createDealsRequest,
			BindingResult result, Principal principal) {
		
		try {
			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
			if (errorMap != null)
				return errorMap;
		
			dealsService.createDeals(createDealsRequest);
			
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to create Deal!  " + msg),
					HttpStatus.BAD_REQUEST);
		}	
		return new ResponseEntity<>(new ApiResponse(true, "Deal Created Successfully!") , HttpStatus.CREATED);			
	}
	
	
	@PostMapping("/UpdateDeals")
	public ResponseEntity<?> UpdateDeals(@Valid @RequestBody CreateOrUpdateDealsRequest updateDealsRequest,BindingResult result, Principal principal) {

		try {
			ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
			if (errorMap != null)
				return errorMap;
			
			if(!(dealsRepository.existsById(updateDealsRequest.getDealId()))) {
		
				return new ResponseEntity(new ApiResponse(false, "Deal with the ID : "+updateDealsRequest.getDealId()+" does not exist"),HttpStatus.BAD_REQUEST);
		
			}
			
			dealsService.updateDeals(updateDealsRequest);		
			
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Update Deal!  " + msg),
					HttpStatus.BAD_REQUEST);
		}
			
			
		return new ResponseEntity(new ApiResponse(true, "Deal has been Updated successfully "), HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/getAllDeals")
	public List<Deals> getAllDeals(Principal principal) {
		List<Deals> listOfDeals = dealsRepository.findAll();
		
		return listOfDeals;
	}
	
	
	@GetMapping("/getAllDealTypes")
	public List<String> getDealType() {
		
		List<String> listOfDealTypes = new ArrayList<>();
		
		listOfDealTypes.add("Existing Business");
		listOfDealTypes.add("New Business");

		return listOfDealTypes;
	}
	
	
	@GetMapping("/getAllDealStages")
	public List<String> getDealStage() {
		
		List<String> listOfDealStages = new ArrayList<>();
		
		listOfDealStages.add("Qualification");
		listOfDealStages.add("Needs Analysis");
		listOfDealStages.add("Value Proposition");
		listOfDealStages.add("Identify Decision Makers");
		listOfDealStages.add("Proposal/ Price Quote");
		listOfDealStages.add("Negotiation/ Review");
		listOfDealStages.add("Closed Won");
		listOfDealStages.add("Closed Lost");
		listOfDealStages.add("Closed-Lost to Competition");
		
		return listOfDealStages;
	}

	@GetMapping("/getAllLeadSource")
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/deleteDeal/{id}")
	 public ResponseEntity<?> deleteLead(@PathVariable long id, Principal principal)throws AppException {
		try {
			
			if(!(dealsHistoryRepository.existsById(id))) {
				
				return new ResponseEntity(new ApiResponse(false, "Deal with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

			}
	
			dealsService.deleteDealById(id);
			
			
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, "Could not delete Deal  " + msg),
					HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity(new ApiResponse(true, "Deal in now deleted"),HttpStatus.OK);
	}
	

	@GetMapping("/getDealsHistory/{id}")
	 public List<DealsHistory> getDealsHistory(@PathVariable long id, Principal principal)throws AppException {
		
		List<DealsHistory> listOfDeals = dealsHistoryRepository.findListOfDealHistory(id);
		
		return listOfDeals;
			
	
	}
}
