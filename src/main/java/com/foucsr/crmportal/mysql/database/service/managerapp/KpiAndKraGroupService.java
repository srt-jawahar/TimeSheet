package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.util.HashSet;
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
public class KpiAndKraGroupService {
	
	
	@Autowired
	private KpiAndKraGroupRepository kpiAndKraGroupRepository;
			
    @Autowired
    private KpiAndKraRepository kpiAndKraRepository;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> createKraKpiGroup(@Valid @RequestBody CreateOrUpdateKpiKraGroupRequest createOrUpdateKpiKraGroupRequest)throws AppException{
    	
    		      
		 Set<KpiAndKra> kpiAndKra= new HashSet<>();
	        Long groups[] = createOrUpdateKpiKraGroupRequest.getKpiIds();
			Long rating;
			rating = (long) 0;
	    	for(int i=0;i<groups.length;i++) {
	    	
//	    		KpiAndKraGroup kpiAndKraGroup2 = kpiAndKraGroupRepository.findById(groups[i]).
//	    		kpiAndKraGroup.add(kpiAndKraGroupRepository.findById(groups[i]));
	    		if(!(kpiAndKraRepository.existsById(groups[i]))) {
	        		throw new AppException("There is no Kpi with this id"+groups[i]);
	               }
		   KpiAndKra kpiAndKra2 = kpiAndKraRepository.findById(groups[i]).orElseThrow(() -> new AppException("Kpi/Kra does not exist."));
            
		   rating = rating + kpiAndKra2.getRating();

		kpiAndKra.add(kpiAndKra2);
	    	}
	    	
	    	if(!(rating == 100)) {
    		throw new AppException("The sum of total rating should be 100");
        	}
	    	
	    	KpiAndKraGroup kpiAndKraGroup = new KpiAndKraGroup(createOrUpdateKpiKraGroupRequest.getName(),createOrUpdateKpiKraGroupRequest.getDescription(),kpiAndKra);
	    	
	    	
	    	kpiAndKraGroupRepository.save(kpiAndKraGroup);
   
    	 return new ResponseEntity(new ApiResponse(true, "Group Created successfully"), HttpStatus.CREATED);
    	
    	
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> updateKraKpiGroup(@Valid @RequestBody CreateOrUpdateKpiKraGroupRequest createOrUpdateKpiKraGroupRequest)throws AppException{
    	
		 Set<KpiAndKra> kpiAndKra= new HashSet<>();
	        Long groups[] = createOrUpdateKpiKraGroupRequest.getKpiIds();
			Long rating;
			rating = (long) 0;
	    	for(int i=0;i<groups.length;i++) {
	    	
//	    		KpiAndKraGroup kpiAndKraGroup2 = kpiAndKraGroupRepository.findById(groups[i]).
//	    		kpiAndKraGroup.add(kpiAndKraGroupRepository.findById(groups[i]));
	    		if(!(kpiAndKraRepository.existsById(groups[i]))) {
	        		throw new AppException("There is no Kpi with this id"+groups[i]);
	               }
		   KpiAndKra kpiAndKra2 = kpiAndKraRepository.findById(groups[i]).orElseThrow(() -> new AppException("Kpi/Kra does not exist."));
            
		   rating = rating + kpiAndKra2.getRating();

		kpiAndKra.add(kpiAndKra2);
	    	}
	    	
	    	if(!(rating == 100)) {
    		throw new AppException("The sum of total rating should be 100");
        	}
		
		   KpiAndKraGroup kpiAndKraGroup = kpiAndKraGroupRepository.findById(createOrUpdateKpiKraGroupRequest.getId()).orElseThrow(() -> new AppException("Kpi/Kra does not exist."));

	    	kpiAndKraGroup.setDescription(createOrUpdateKpiKraGroupRequest.getDescription());
	    	kpiAndKraGroup.setName(createOrUpdateKpiKraGroupRequest.getName());
	    	kpiAndKraGroup.setKpiAndKra(kpiAndKra);
	    	kpiAndKraGroupRepository.save(kpiAndKraGroup);
   
    	 return new ResponseEntity(new ApiResponse(true, "Group Updated successfully"), HttpStatus.OK);
    	
    	
    }
	
	
}
