package com.foucsr.crmportal.mysql.database.service.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.crm.FilesInfo;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.repository.crm.LeadsRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.crm.ConvertLeadToContactRequest;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateContacts;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateLeadRequest;

@Service
public class LeadsService {
	
	@Autowired
	LeadsRepository leadsRepository;
	
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity<?> createLeads(@Valid @RequestBody CreateOrUpdateLeadRequest leadRequest)throws AppException{
		  Leads leads = new Leads();
			leads.setStage("LEAD");
			
			leads.setLeadOwner(leadRequest.getLeadOwner());
			leads.setCompany(leadRequest.getCompany());
			leads.setFirstName(leadRequest.getFirstName());
			leads.setLastName(leadRequest.getLastName());
			leads.setTitle(leadRequest.getTitle());
			leads.setPhone(leadRequest.getPhone());
			leads.setMobile(leadRequest.getMobile());
			leads.setEmail(leadRequest.getEmail());
			leads.setFax(leadRequest.getFax());
			leads.setWebsite(leadRequest.getWebsite());
			leads.setLeadSource(leadRequest.getLeadSource());
			leads.setLeadStatus(leadRequest.getLeadStatus());
			leads.setIndustry(leadRequest.getIndustry());
			leads.setNoOfEmployees(leadRequest.getNoOfEmployees());
			leads.setAnnualRevenue(leadRequest.getAnnualRevenue());
			leads.setRating(leadRequest.getRating());
			leads.setSkypeId(leadRequest.getSkypeId());
			leads.setSecondaryEmail(leadRequest.getSecondaryEmail());
			leads.setDescription(leadRequest.getDescription());
			leads.setAddressInfo(leadRequest.getAddressInfo());
			
			leadsRepository.save(leads);
			
			
			 return new ResponseEntity(new ApiResponse(true, "Lead Created successfully"), HttpStatus.CREATED);

  
	  }
	    	
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
			public ResponseEntity<?> updateLeads(@Valid @RequestBody CreateOrUpdateLeadRequest leadRequest)throws AppException{
			  Leads leads = leadsRepository.findById(leadRequest.getLeadId()).orElseThrow(
						() -> new ResourceNotFoundException("Lead with this Id  does not exist!", "id", leadRequest.getLeadId()));
				
			  
			  leads.setStage("LEAD");
				
				leads.setLeadOwner(leadRequest.getLeadOwner());
				leads.setCompany(leadRequest.getCompany());
				leads.setFirstName(leadRequest.getFirstName());
				leads.setLastName(leadRequest.getLastName());
				leads.setTitle(leadRequest.getTitle());
				leads.setPhone(leadRequest.getPhone());
				leads.setMobile(leadRequest.getMobile());
				leads.setEmail(leadRequest.getEmail());
				leads.setFax(leadRequest.getFax());
				leads.setWebsite(leadRequest.getWebsite());
				leads.setLeadSource(leadRequest.getLeadSource());
				leads.setLeadStatus(leadRequest.getLeadStatus());
				leads.setIndustry(leadRequest.getIndustry());
				leads.setNoOfEmployees(leadRequest.getNoOfEmployees());
				leads.setAnnualRevenue(leadRequest.getAnnualRevenue());
				leads.setRating(leadRequest.getRating());
				leads.setSkypeId(leadRequest.getSkypeId());
				leads.setSecondaryEmail(leadRequest.getSecondaryEmail());
				leads.setDescription(leadRequest.getDescription());
				leads.setAddressInfo(leadRequest.getAddressInfo());
				
				leadsRepository.save(leads);
				
				
				 return new ResponseEntity(new ApiResponse(true, "Lead Updated successfully"), HttpStatus.OK);

	  
		  }
	  
	  
	  @SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity<?> createContacts(@Valid @RequestBody CreateOrUpdateContacts leadRequest)throws AppException{
		  Leads leads = new Leads();
			leads.setStage("CONTACT");
			
			leads.setLeadOwner(leadRequest.getContactOwner());
			leads.setFirstName(leadRequest.getFirstName());
			leads.setLastName(leadRequest.getLastName());
			leads.setTitle(leadRequest.getTitle());
			leads.setPhone(leadRequest.getPhone());
			leads.setMobile(leadRequest.getMobile());
			leads.setEmail(leadRequest.getEmail());
			leads.setFax(leadRequest.getFax());
			leads.setWebsite(leadRequest.getWebsite());
			leads.setLeadSource(leadRequest.getLeadSource());
			
			leads.setAccountName(leadRequest.getAccountName());
			leads.setDepartment(leadRequest.getDepartment());
			leads.setAssistant(leadRequest.getAssistant());
			leads.setVendorName(leadRequest.getVendorName());
			
			leads.setDob(leadRequest.getDob());
			leads.setAssistantPhone(leadRequest.getAssistantPhone());
			leads.setReportingTo(leadRequest.getReportingTo());

			
			leads.setSkypeId(leadRequest.getSkypeId());
			leads.setSecondaryEmail(leadRequest.getSecondaryEmail());
			leads.setDescription(leadRequest.getDescription());
			leads.setAddressInfo(leadRequest.getAddressInfo());
			
			leadsRepository.save(leads);
			
			
			 return new ResponseEntity(new ApiResponse(true, "Contact Created successfully"), HttpStatus.CREATED);


	  }
	
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
			public ResponseEntity<?> updateContacts(@Valid @RequestBody CreateOrUpdateContacts leadRequest)throws AppException{
		  Leads leads = leadsRepository.findById(leadRequest.getLeadId()).orElseThrow(
					() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", leadRequest.getLeadId()));
							
		       leads.setStage("CONTACT");
				
				leads.setLeadOwner(leadRequest.getContactOwner());
				leads.setFirstName(leadRequest.getFirstName());
				leads.setLastName(leadRequest.getLastName());
				leads.setTitle(leadRequest.getTitle());
				leads.setPhone(leadRequest.getPhone());
				leads.setMobile(leadRequest.getMobile());
				leads.setEmail(leadRequest.getEmail());
				leads.setFax(leadRequest.getFax());
				leads.setWebsite(leadRequest.getWebsite());
				leads.setLeadSource(leadRequest.getLeadSource());
				
				leads.setAccountName(leadRequest.getAccountName());
				leads.setDepartment(leadRequest.getDepartment());
				leads.setAssistant(leadRequest.getAssistant());
				leads.setVendorName(leadRequest.getVendorName());
				
				leads.setDob(leadRequest.getDob());
				leads.setAssistantPhone(leadRequest.getAssistantPhone());
				leads.setReportingTo(leadRequest.getReportingTo());


				
				leads.setSkypeId(leadRequest.getSkypeId());
				leads.setSecondaryEmail(leadRequest.getSecondaryEmail());
				leads.setDescription(leadRequest.getDescription());
				leads.setAddressInfo(leadRequest.getAddressInfo());
				
				leadsRepository.save(leads);
				
				
				 return new ResponseEntity(new ApiResponse(true, "Contact Updated successfully"), HttpStatus.OK);


		  }
		
	  
	  
	  @SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity<?> convertContacts(@Valid @RequestBody ConvertLeadToContactRequest leadRequest)throws AppException{
	  Leads leads = leadsRepository.findById(leadRequest.getLeadId()).orElseThrow(
				() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", leadRequest.getLeadId()));
						
	       leads.setStage("CONTACT");
			
			leads.setLeadOwner(leadRequest.getContactOwner());
		
			
			leads.setTitle(leadRequest.getAccountName());
			leads.setTitle(leadRequest.getDepartment());
			leads.setTitle(leadRequest.getAssistant());
			leads.setTitle(leadRequest.getVendorName());
			
			leads.setTitle(leadRequest.getDob());
			leads.setTitle(leadRequest.getAssistantPhone());
			leads.setTitle(leadRequest.getReportingTo());

			
			
			leadsRepository.save(leads);
			
			
			 return new ResponseEntity(new ApiResponse(true, "Lead is converted into Contact successfully"), HttpStatus.OK);


	  }
	
	
	
	   public void deleteLeadById(long id) throws AppException {

		Leads leads = leadsRepository.findById(id).orElseThrow(() -> new AppException("Lead/Contact does not exist."));
		
		leadsRepository.delete(leads);
		
		}
	
	   public Leads getLeadOrContact(long id) throws AppException{
		   
		Leads leads = leadsRepository.findById(id).orElseThrow(() -> new AppException("Lead/Contact does not exist."));
		
		return leads;

	  }
	   
	   
	   @SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity<?> addFileInfo(long id,@Valid @RequestBody FilesInfo filesInfo)throws AppException{
		   
		   Leads leads = leadsRepository.findById(id).orElseThrow(
					() -> new ResourceNotFoundException("Contact with this Id  does not exist!", "id", id));
		   
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR, 5);
			c.add(Calendar.MINUTE, 30);
			Date newTime = c.getTime();
			
		   filesInfo.setDate(sdf.format(newTime));
		   List<FilesInfo> newList = leads.getFileInfo();
		   newList.add(filesInfo);
		   leads.setFileInfo(newList);
		   
		   leadsRepository.save(leads);
		   
		   
			 return new ResponseEntity(new ApiResponse(true, "File uploaded successfully"), HttpStatus.OK);

	   }
	   

}
