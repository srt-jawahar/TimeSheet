package com.foucsr.crmportal.mysql.database.controller.managerapp;

import java.security.Principal;
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
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraRepository;
import com.foucsr.crmportal.mysql.database.service.managerapp.KpiAndKraService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.managerapp.CreateOrUpdateKpiKraGroupRequest;

@RestController
@RequestMapping("/api/KpiAndKra/Service")
public class KpiAndKraController {
	
	@Autowired
	private KpiAndKraRepository kpiAndKraRepository;
	
	@Autowired
	private KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@Autowired
	private KpiAndKraService kpiAndKraService;
	
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Create")
	public ResponseEntity<?> createKpiKra(@Valid @RequestBody KpiAndKra kpiAndKra,
			BindingResult result, Principal principal) {

	try {
		
		
         kpiAndKraRepository.save(kpiAndKra);
		
		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to create Kpi and Kra!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra has been Created successfully "), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Update")
	public ResponseEntity<?> UpdateKpiKra(@Valid @RequestBody KpiAndKra kpiAndKra,
			BindingResult result, Principal principal) {

	try {
				if(kpiAndKra.getId()==0) {
					return new ResponseEntity(new ApiResponse(false, "Please specify KPI/KRA Id to Update."),HttpStatus.BAD_REQUEST);
				}
				if(!(kpiAndKraRepository.existsById(kpiAndKra.getId()))) {
		    		return new ResponseEntity(new ApiResponse(false,"KPI/KRA With this Id does not exist"),HttpStatus.BAD_REQUEST);  
		    	}					
				
		
		kpiAndKraService.updateKraKpi(kpiAndKra);
 
		
		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to update Kpi and Kra!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra has been Updated successfully "), HttpStatus.OK);
	}
	
	
	@GetMapping("/getListOfKpiAndKra")
	public List<KpiAndKra> getAllProjects(Principal principal) {
		List<KpiAndKra> listofCandidates = kpiAndKraRepository.findAll();
		return listofCandidates;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/delete/{kpiID}")
	public ResponseEntity<?> deleteKpiKra(@PathVariable long kpiID){
		try {
			if(!(kpiAndKraRepository.existsById(kpiID))) {
				return new ResponseEntity(new ApiResponse(false,"KpiAndKpr does not exist"),HttpStatus.BAD_REQUEST);
			}
			kpiAndKraRepository.deleteByIdInMap(kpiID);
			kpiAndKraService.deleteKpi(kpiID);
			
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Delete Kpi and Kra ! " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Kpi and Kra has been Deleted successfully "), HttpStatus.OK);
		}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("/Multipledelete")
    public ResponseEntity<?> deleteKpiKra(@Valid @RequestBody MultipleSelectRequest multipleSelectRequest){
    	try {
    		Long ids[] = multipleSelectRequest.getIds();
        	for(int i=0;i<ids.length;i++) {
    		
    		if(!(kpiAndKraRepository.existsById(ids[i]))) {
    			return new ResponseEntity(new ApiResponse(false,"KpiAndKpr does not exist"),HttpStatus.BAD_REQUEST);
    		}
    		kpiAndKraRepository.deleteByIdInMap(ids[i]);
    		kpiAndKraService.deleteKpi(ids[i]);
        	}
    		
    	}catch(Exception ex) {
    		String msg = ex.getMessage() != null ? ex.getMessage() : "";

    		String cause = "";

    		if (ex.getCause() != null) {

    			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

    			msg = msg + "!!!" + cause;
    		}
    		return new ResponseEntity(new ApiResponse(false, " Unable to Delete Kpi and Kra ! " + msg),
    				HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra has been Deleted successfully "), HttpStatus.OK);
    	}	
	
	  
	
		
		
			
			
			
			
	
	
	
	
	
	
	
    }
	
	
	


