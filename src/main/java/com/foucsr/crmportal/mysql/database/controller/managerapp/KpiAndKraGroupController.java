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
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.service.managerapp.KpiAndKraGroupService;
import com.foucsr.crmportal.mysql.database.service.managerapp.KpiAndKraService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.MultipleSelectRequest;
import com.foucsr.crmportal.payload.managerapp.CreateOrUpdateKpiKraGroupRequest;

@RestController
@RequestMapping("/api/KpiAndKraGroup/Service")
public class KpiAndKraGroupController {
	
	@Autowired
	private KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@Autowired
	private KpiAndKraService kpiAndKraService;
	

	@Autowired
	private KpiAndKraGroupService kpiAndKraGroupService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Create")
	public ResponseEntity<?> createKpiKraGroup(@Valid @RequestBody CreateOrUpdateKpiKraGroupRequest kpiAndKraGroup,
			BindingResult result, Principal principal) {

	try {
		if(kpiAndKraGroup.getName() == null) {
			return new ResponseEntity(new ApiResponse(false, "Group name cannot be blank"),HttpStatus.BAD_REQUEST);
		}
		if(kpiAndKraGroupRepository.existsByName(kpiAndKraGroup.getName())) {
    		return new ResponseEntity(new ApiResponse(false,"Group name already taken"),HttpStatus.BAD_REQUEST);  
    	}
		if(kpiAndKraGroup.getKpiIds()==null) {
			return new ResponseEntity(new ApiResponse(false, "Please specify KPI/KRA to add in the group"),HttpStatus.BAD_REQUEST);
		}

		   kpiAndKraGroupService.createKraKpiGroup(kpiAndKraGroup);
		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to create Kpi and Kra group!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra group has been Created successfully "), HttpStatus.CREATED);
	}
		


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/Update")
	public ResponseEntity<?> UpdateKpiKraGroupGroup(@Valid @RequestBody CreateOrUpdateKpiKraGroupRequest kpiAndKraGroup,
			BindingResult result, Principal principal) {

	try {
		if(kpiAndKraGroup.getId()==0) {
			return new ResponseEntity(new ApiResponse(false, "Please specify Group Id to Update."),HttpStatus.BAD_REQUEST);
		}
		if(!(kpiAndKraGroupRepository.existsById(kpiAndKraGroup.getId()))) {
    		return new ResponseEntity(new ApiResponse(false,"Group With this Id does not exist"),HttpStatus.BAD_REQUEST);  
    	}
		if(kpiAndKraGroup.getName() == null) {
			return new ResponseEntity(new ApiResponse(false, "Group name cannot be blank"),HttpStatus.BAD_REQUEST);
		}
	    if(kpiAndKraGroup.getKpiIds()==null) {
		return new ResponseEntity(new ApiResponse(false, "Please specify KPI/KRA to add in the group"),HttpStatus.BAD_REQUEST);
	    }

		KpiAndKraGroup newkrp = kpiAndKraGroupRepository.findById(kpiAndKraGroup.getId()).orElseThrow(() -> new AppException("Kpi and Kra group does not exist."));
		
		String name = newkrp.getName();
		if(!(name.equals(kpiAndKraGroup.getName()))) {

		if(kpiAndKraGroupRepository.existsByName(kpiAndKraGroup.getName())) {
    		return new ResponseEntity(new ApiResponse(false,"Group name already taken"),HttpStatus.BAD_REQUEST);  
    	}
		}
		
		
		kpiAndKraGroupService.updateKraKpiGroup(kpiAndKraGroup);
		
		
	}catch(Exception ex) {
		String msg = ex.getMessage() != null ? ex.getMessage() : "";

		String cause = "";

		if (ex.getCause() != null) {

			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

			msg = msg + "!!!" + cause;
		}
		return new ResponseEntity(new ApiResponse(false, " Unable to update Kpi and Kra group!  " + msg),
				HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra group has been Updated successfully "), HttpStatus.OK);
	}
	
	
	

	@GetMapping("/getListOfKpiAndKraGroups")
	public List<KpiAndKraGroup> getAllProjects(Principal principal) {
		List<KpiAndKraGroup> listofCandidates = kpiAndKraGroupRepository.findAll();

//		List<KpiAndKraGroup> listofCandidates = kpiAndKraGroupRepository.findListOfKpiGroups();
		return listofCandidates;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/delete/{kpiGroupID}")
	public ResponseEntity<?> deleteKpiKraGroup(@PathVariable long kpiGroupID){
		try {
			if(!(kpiAndKraGroupRepository.existsById(kpiGroupID))) {
				return new ResponseEntity(new ApiResponse(false,"KpiAndKpr Group does not exist"),HttpStatus.BAD_REQUEST);
			}
			kpiAndKraGroupRepository.deleteByIdInMap(kpiGroupID);
			kpiAndKraService.deleteKpiGroup(kpiGroupID);
			
		}catch(Exception ex) {
			String msg = ex.getMessage() != null ? ex.getMessage() : "";

			String cause = "";

			if (ex.getCause() != null) {

				cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

				msg = msg + "!!!" + cause;
			}
			return new ResponseEntity(new ApiResponse(false, " Unable to Delete Kpi and Kra Group! " + msg),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(new ApiResponse(true, "Kpi and Kra group has been Deleted successfully "), HttpStatus.OK);
		}		
	


        @SuppressWarnings({ "rawtypes", "unchecked" })
        @PostMapping("/Multipledelete")
        public ResponseEntity<?> deleteKpiKraGroup(@Valid @RequestBody MultipleSelectRequest multipleSelectRequest){
        	try {
        		Long ids[] = multipleSelectRequest.getIds();
            	for(int i=0;i<ids.length;i++) {
        		
        		if(!(kpiAndKraGroupRepository.existsById(ids[i]))) {
        			return new ResponseEntity(new ApiResponse(false,"KpiAndKpr Group does not exist"),HttpStatus.BAD_REQUEST);
        		}
        		kpiAndKraGroupRepository.deleteByIdInMap(ids[i]);
        		kpiAndKraService.deleteKpiGroup(ids[i]);
            	}
        		
        	}catch(Exception ex) {
        		String msg = ex.getMessage() != null ? ex.getMessage() : "";

        		String cause = "";

        		if (ex.getCause() != null) {

        			cause = ex.getCause().getMessage() != null ? ex.getCause().getMessage() : "";

        			msg = msg + "!!!" + cause;
        		}
        		return new ResponseEntity(new ApiResponse(false, " Unable to Delete Kpi and Kra groups! " + msg),
        				HttpStatus.BAD_REQUEST);
        	}
        	return new ResponseEntity(new ApiResponse(true, "Kpi and Kra groups has been Deleted successfully "), HttpStatus.OK);
        	}	
        
        
       
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
    	@GetMapping("/getSingleGroup/{id}")
         public ResponseEntity<?> getListOfKpisInAGroup(@PathVariable long id, Principal principal)throws AppException {
    		
    			
    			if(!(kpiAndKraGroupRepository.existsById(id))) {
    				
    				return new ResponseEntity(new ApiResponse(false, "Group with the ID : "+id+" does not exist"),HttpStatus.BAD_REQUEST);

    			}
    			
    			KpiAndKraGroup newGrp =  kpiAndKraGroupRepository.findById(id).orElseThrow(() -> new AppException("Kpi and Kra group does not exist."));
            	
    			
    			
    		return  ResponseEntity.status(HttpStatus.OK).body(newGrp);   
         }
        
        
        
        
        
        
        }


   
      
	
	


