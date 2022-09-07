package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKra;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraGroupRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.KpiAndKraRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.managerapp.CreateOrUpdateKpiKraGroupRequest;

@Service
public class KpiAndKraService {
	
	@Autowired
	private KpiAndKraGroupRepository kpiAndKraGroupRepository;
	
	@Autowired
	private KpiAndKraRepository kpiAndKraRepository;
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> updateKraKpi(@Valid @RequestBody KpiAndKra kpiAndKra)throws AppException{
    	
 
	    	KpiAndKra kpiAndKra2 = kpiAndKraRepository.findById(kpiAndKra.getId()).orElseThrow(() -> new AppException("Kpi and Kra does not exist."));
	    			
	    	kpiAndKra2.setName(kpiAndKra.getName());
	    	kpiAndKra2.setDescription(kpiAndKra.getDescription());
	    	kpiAndKra2.setRating(kpiAndKra.getRating());  	
	    	
	    	kpiAndKraRepository.save(kpiAndKra2);
   
    	 return new ResponseEntity(new ApiResponse(true, "Kpi/Kra Updated successfully"), HttpStatus.OK);
    	
    	
    }


	public void deleteKpi(long kpiID) {
		
		KpiAndKra kpiAndKra = kpiAndKraRepository.findById(kpiID).orElseThrow(() -> new AppException("Kpi and Kra does not exist."));
		kpiAndKraRepository.delete(kpiAndKra);
		
	}


	public void deleteKpiGroup(long kpiGroupID) {
		KpiAndKraGroup kpiAndKra = kpiAndKraGroupRepository.findById(kpiGroupID).orElseThrow(() -> new AppException("Kpi and Kra does not exist."));
		kpiAndKraGroupRepository.delete(kpiAndKra);		
	}
	
	
}
