package com.foucsr.crmportal.mysql.database.service.crm;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.crm.Deals;
import com.foucsr.crmportal.mysql.database.model.crm.DealsHistory;
import com.foucsr.crmportal.mysql.database.repository.crm.DealsRepository;
import com.foucsr.crmportal.mysql.database.repository.crm.DealsHistoryRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.crm.CreateOrUpdateDealsRequest;



@Service
public class DealsService {

	@Autowired
	private DealsRepository dealsRepository;
	
	@Autowired
	private DealsHistoryRepository dealsHistoryRepository;
	
	public ResponseEntity<?> createDeals(CreateOrUpdateDealsRequest createDealsRequest)throws AppException {
	
		
		Deals newDeal = new Deals();
		DealsHistory dealsHistory = new DealsHistory();
		
		
		if((createDealsRequest.getDealName())==null||(createDealsRequest.getDealName().equals("")) ){
			
			throw new AppException("Please Specify Deal name!");
			
		}
		
		if(dealsRepository.existsByDealName(createDealsRequest.getDealName())) {
			
			throw new AppException("Deal with the name "+createDealsRequest.getDealName()+" already exists!!");
			
		}
		
		newDeal.setDealName(createDealsRequest.getDealName());
	
		if((createDealsRequest.getDealOwner())==null||(createDealsRequest.getDealOwner().equals("")) ){
			
			throw new AppException("Please Specify Deal Owner!");
			
		}
		newDeal.setDealOwner(createDealsRequest.getDealOwner());
		
		newDeal.setAmount(createDealsRequest.getAmount());
		
		if((createDealsRequest.getClosingDate()==null)||(createDealsRequest.getClosingDate().equals(""))||!(createDealsRequest.getClosingDate().length()==10)) {
			
			throw new AppException("Please set Closing Date");
		}
		newDeal.setClosingDate(createDealsRequest.getClosingDate());
		
		newDeal.setNextStep(createDealsRequest.getNextStep());
		
		newDeal.setDealType(createDealsRequest.getDealType());
		
	switch (createDealsRequest.getStage()) {
		case "Qualification":
			double expectedRevenue1 = (createDealsRequest.getAmount()/100)*10;
			newDeal.setProbability(10);
			newDeal.setExpectedRevenue(expectedRevenue1);
			dealsHistory.setProbability(10);
			dealsHistory.setExpectedRevenue(expectedRevenue1);
			break;
			
		case "Needs Analysis":
			double expectedRevenue2 = (createDealsRequest.getAmount()/100)*20;
			newDeal.setProbability(20);
			newDeal.setExpectedRevenue(expectedRevenue2);
			dealsHistory.setProbability(20);
			dealsHistory.setExpectedRevenue(expectedRevenue2);
			break;
			
		case "Value Proposition":
			double expectedRevenue3 = (createDealsRequest.getAmount()/100)*40;
			newDeal.setProbability(40);
			newDeal.setExpectedRevenue(expectedRevenue3);
			dealsHistory.setProbability(40);
			dealsHistory.setExpectedRevenue(expectedRevenue3);
			break;
			
		case "Identify Decision Makers":
			double expectedRevenue4 = (createDealsRequest.getAmount()/100)*60;
			newDeal.setProbability(60);
			newDeal.setExpectedRevenue(expectedRevenue4);
			dealsHistory.setProbability(60);
			dealsHistory.setExpectedRevenue(expectedRevenue4);
			break;
			
		case "Proposal/ Price Quote":
			double expectedRevenue5 = (createDealsRequest.getAmount()/100)*75;
			newDeal.setProbability(75);
			newDeal.setExpectedRevenue(expectedRevenue5);
			dealsHistory.setProbability(75);
			dealsHistory.setExpectedRevenue(expectedRevenue5);
			break;
			
		case "Negotiation/ Review":
			double expectedRevenue6 = (createDealsRequest.getAmount()/100)*90;
			newDeal.setProbability(90);
			newDeal.setExpectedRevenue(expectedRevenue6);
			dealsHistory.setProbability(90);
			dealsHistory.setExpectedRevenue(expectedRevenue6);
			break;
			
		case "Closed Won":
			newDeal.setProbability(100);
			newDeal.setExpectedRevenue(createDealsRequest.getAmount());
			dealsHistory.setProbability(100);
			dealsHistory.setExpectedRevenue(createDealsRequest.getAmount());
			break;
			
		case "Closed Lost":
			newDeal.setProbability(0);
			newDeal.setExpectedRevenue(0);
			dealsHistory.setProbability(0);
			dealsHistory.setExpectedRevenue(0);
			break;
			
		case "Closed-Lost to Competition":
			newDeal.setProbability(0);
			newDeal.setExpectedRevenue(0);
			dealsHistory.setProbability(0);
			dealsHistory.setExpectedRevenue(0);
			break;
		
		}
		
		newDeal.setStage(createDealsRequest.getStage());
		
		newDeal.setLeadSource(createDealsRequest.getLeadSource());
		
		/* if((createDealsRequest.getAccountName())==null||(createDealsRequest.getAccountName().equals("")) ){
			
			throw new AppException("Please Specify Account name!");
			
		}
		
		if(!(accountsRepository.existsByAccountName(createDealsRequest.getAccountName()))) {
			
			throw new AppException("Account with the name "+createDealsRequest.getAccountName()+" does not exists!!");
			
		} */
		
		newDeal.setAccountName(createDealsRequest.getAccountName());
		
		newDeal.setContactName(createDealsRequest.getContactName());

		newDeal.setDescription(createDealsRequest.getDescription());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, 5);
		c.add(Calendar.MINUTE, 30);
		Date newTime = c.getTime();
		
		newDeal.setTimeEdited(sdf.format(newTime));
		
		dealsRepository.save(newDeal);

		
		dealsHistory.setDealId(newDeal.getDealId());
		dealsHistory.setDealOwner(createDealsRequest.getDealOwner());
		dealsHistory.setDealName(createDealsRequest.getDealName());
		dealsHistory.setAccountName(createDealsRequest.getAccountName());
		dealsHistory.setDealType(createDealsRequest.getDealType());
		dealsHistory.setNextStep(createDealsRequest.getNextStep());
		dealsHistory.setLeadSource(createDealsRequest.getLeadSource());
		dealsHistory.setContactName(createDealsRequest.getContactName());
		dealsHistory.setAmount(createDealsRequest.getAmount());
		dealsHistory.setClosingDate(createDealsRequest.getClosingDate());
		dealsHistory.setStage(createDealsRequest.getStage());
		dealsHistory.setDescription(createDealsRequest.getDescription());
		dealsHistory.setTimeEdited(sdf.format(newTime));
		
		dealsHistoryRepository.save(dealsHistory);
		
		
		
		return new ResponseEntity<>(new ApiResponse(true, "Deal Created Successfully!") , HttpStatus.CREATED);
//		return null;
	
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<?> updateDeals(@Valid @RequestBody CreateOrUpdateDealsRequest updateDealsRequest )throws AppException{
	
		DealsHistory dealsHistory = new DealsHistory();
		Deals deal = dealsRepository.findById(updateDealsRequest.getDealId()).orElseThrow(
				() -> new ResourceNotFoundException("Deal with this Id  does not exist!", "id", updateDealsRequest.getDealId()));
	
		long dealIdFromRequest = updateDealsRequest.getDealId();	
		String dealNameFromRepository = dealsRepository.findDealNameByDealId(dealIdFromRequest);
		
		
		if((updateDealsRequest.getDealName())==null||(updateDealsRequest.getDealName().equals("")) ){
			
			throw new AppException("Please Specify Deal name!");
			
		}
		
		System.out.println(dealNameFromRepository);
		System.out.println(updateDealsRequest.getDealName());
		
		if (!(updateDealsRequest.getDealName().equals(dealNameFromRepository))) {
			if(dealsRepository.existsByDealName(updateDealsRequest.getDealName())) {
				
				throw new AppException("Deal with the name "+updateDealsRequest.getDealName()+" already exists!!");
				
			}
		} 
		
		
		deal.setDealName(updateDealsRequest.getDealName());
	
		if((updateDealsRequest.getDealOwner())==null||(updateDealsRequest.getDealOwner().equals("")) ){
			
			throw new AppException("Please Specify Deal Owner!");
			
		}
		deal.setDealOwner(updateDealsRequest.getDealOwner());
		
		deal.setAmount(updateDealsRequest.getAmount());
		
		if((updateDealsRequest.getClosingDate()==null)||(updateDealsRequest.getClosingDate().equals(""))||!(updateDealsRequest.getClosingDate().length()==10)) {
			
			throw new AppException("Please set Closing Date");
		}
		deal.setClosingDate(updateDealsRequest.getClosingDate());
		
		deal.setNextStep(updateDealsRequest.getNextStep());
		
		deal.setDealType(updateDealsRequest.getDealType());
		
	switch (updateDealsRequest.getStage()) {
		case "Qualification":
			double expectedRevenue1 = (updateDealsRequest.getAmount()/100)*10;
			deal.setProbability(10);
			deal.setExpectedRevenue(expectedRevenue1);
			dealsHistory.setProbability(10);
			dealsHistory.setExpectedRevenue(expectedRevenue1);
			break;
			
		case "Needs Analysis":
			double expectedRevenue2 = (updateDealsRequest.getAmount()/100)*20;
			deal.setProbability(20);
			deal.setExpectedRevenue(expectedRevenue2);
			dealsHistory.setProbability(20);
			dealsHistory.setExpectedRevenue(expectedRevenue2);
			break;
			
		case "Value Proposition":
			double expectedRevenue3 = (updateDealsRequest.getAmount()/100)*40;
			deal.setProbability(40);
			deal.setExpectedRevenue(expectedRevenue3);
			dealsHistory.setProbability(40);
			dealsHistory.setExpectedRevenue(expectedRevenue3);
			break;
			
		case "Identify Decision Makers":
			double expectedRevenue4 = (updateDealsRequest.getAmount()/100)*60;
			deal.setProbability(60);
			deal.setExpectedRevenue(expectedRevenue4);
			dealsHistory.setProbability(60);
			dealsHistory.setExpectedRevenue(expectedRevenue4);
			break;
			
		case "Proposal/ Price Quote":
			double expectedRevenue5 = (updateDealsRequest.getAmount()/100)*75;
			deal.setProbability(75);
			deal.setExpectedRevenue(expectedRevenue5);
			dealsHistory.setProbability(75);
			dealsHistory.setExpectedRevenue(expectedRevenue5);
			break;
			
		case "Negotiation/ Review":
			double expectedRevenue6 = (updateDealsRequest.getAmount()/100)*90;
			deal.setProbability(90);
			deal.setExpectedRevenue(expectedRevenue6);
			dealsHistory.setProbability(90);
			dealsHistory.setExpectedRevenue(expectedRevenue6);
			break;
			
		case "Closed Won":
			deal.setProbability(100);
			deal.setExpectedRevenue(updateDealsRequest.getAmount());
			dealsHistory.setProbability(100);
			dealsHistory.setExpectedRevenue(updateDealsRequest.getAmount());
			break;
			
		case "Closed Lost":
			deal.setProbability(0);
			deal.setExpectedRevenue(0);
			dealsHistory.setProbability(0);
			dealsHistory.setExpectedRevenue(0);
			break;
			
		case "Closed-Lost to Competition":
			deal.setProbability(0);
			deal.setExpectedRevenue(0);
			dealsHistory.setProbability(0);
			dealsHistory.setExpectedRevenue(0);
			break;
		
		}
		
	deal.setStage(updateDealsRequest.getStage());
		
	deal.setLeadSource(updateDealsRequest.getLeadSource());
		
		/* if((createDealsRequest.getAccountName())==null||(updateDealsRequest.getAccountName().equals("")) ){
			
			throw new AppException("Please Specify Account name!");
			
		}
		
		if(!(accountsRepository.existsByAccountName(updateDealsRequest.getAccountName()))) {
			
			throw new AppException("Account with the name "+updateDealsRequest.getAccountName()+" does not exists!!");
			
		} */
		
	deal.setAccountName(updateDealsRequest.getAccountName());
		
	deal.setContactName(updateDealsRequest.getContactName());
	
	deal.setDescription(updateDealsRequest.getDescription());
		
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.add(Calendar.HOUR, 5);
	c.add(Calendar.MINUTE, 30);
	Date newTime = c.getTime();
	
	deal.setTimeEdited(sdf.format(newTime));
	
		dealsRepository.save(deal);
		
		
		dealsHistory.setDealId(updateDealsRequest.getDealId());
		dealsHistory.setDealOwner(updateDealsRequest.getDealOwner());
		dealsHistory.setDealName(updateDealsRequest.getDealName());
		dealsHistory.setAccountName(updateDealsRequest.getAccountName());
		dealsHistory.setDealType(updateDealsRequest.getDealType());
		dealsHistory.setNextStep(updateDealsRequest.getNextStep());
		dealsHistory.setLeadSource(updateDealsRequest.getLeadSource());
		dealsHistory.setContactName(updateDealsRequest.getContactName());
		dealsHistory.setAmount(updateDealsRequest.getAmount());
		dealsHistory.setClosingDate(updateDealsRequest.getClosingDate());
		dealsHistory.setStage(updateDealsRequest.getStage());
		dealsHistory.setDescription(updateDealsRequest.getDescription());
		dealsHistory.setTimeEdited(sdf.format(newTime));
		
		dealsHistoryRepository.save(dealsHistory);
	
		 return new ResponseEntity(new ApiResponse(true, "Deal Updated successfully"), HttpStatus.OK);
	}
	
	
	public void deleteDealById(long id) throws AppException {
	
		Deals deal = dealsRepository.findById(id).orElseThrow(() -> new AppException("Deal does not exist."));
	
		dealsRepository.delete(deal);
	
	}
	
	
	
}
