package com.foucsr.crmportal.mysql.database.service.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.exception.ResourceNotFoundException;
import com.foucsr.crmportal.mysql.database.model.crm.Accounts;
import com.foucsr.crmportal.mysql.database.model.crm.Leads;
import com.foucsr.crmportal.mysql.database.repository.crm.AccountsRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.crm.CreateAccountRequest;

@Service
public class AccountsService {
	
	@Autowired
	AccountsRepository accountsRepository;
	
	
	public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest) throws AppException {
			
		    Accounts accs = new Accounts();
			accs.setAccountOwner(createAccountRequest.getAccountOwner());
			accs.setAccountName(createAccountRequest.getAccountName());
			accs.setAccountNumber(createAccountRequest.getAccountNumber());
			accs.setAccountSite(createAccountRequest.getAccountSite());
			accs.setAccountType(createAccountRequest.getAccountType());
			accs.setEmployees(createAccountRequest.getEmployees());
			accs.setAnnualRevenue(createAccountRequest.getAnnualRevenue());
			accs.setParentAccoount(createAccountRequest.getParentAccoount());
			accs.setIndustry(createAccountRequest.getIndustry());
		    accs.setOwnership(createAccountRequest.getOwnership());
		    accs.setSicCode(createAccountRequest.getSicCode());
		    accs.setWebsite(createAccountRequest.getWebsite());
		    accs.setPhone(createAccountRequest.getPhone());
		    accs.setDescription(createAccountRequest.getDescription());
		    accs.setAddressInfo(createAccountRequest.getAddressInfo());
		    
		    accountsRepository.save(accs);
		    
		return new ResponseEntity<>(new ApiResponse(true, "Account Created Successfully!") , HttpStatus.CREATED);

	}
	
	
	public ResponseEntity<?> updateAccount(CreateAccountRequest createAccountRequest) throws AppException {
		Accounts accs = accountsRepository.findById(createAccountRequest.getAccountId()).orElseThrow(
				() -> new ResourceNotFoundException("Account with this Id  does not exist!", "id", createAccountRequest.getAccountId()));
			
		accs.setAccountOwner(createAccountRequest.getAccountOwner());
		accs.setAccountName(createAccountRequest.getAccountName());
		accs.setAccountNumber(createAccountRequest.getAccountNumber());
		accs.setAccountSite(createAccountRequest.getAccountSite());
		accs.setAccountType(createAccountRequest.getAccountType());
		accs.setEmployees(createAccountRequest.getEmployees());
		accs.setAnnualRevenue(createAccountRequest.getAnnualRevenue());
		accs.setParentAccoount(createAccountRequest.getParentAccoount());
		accs.setIndustry(createAccountRequest.getIndustry());
	    accs.setOwnership(createAccountRequest.getOwnership());
	    accs.setSicCode(createAccountRequest.getSicCode());
	    accs.setWebsite(createAccountRequest.getWebsite());
	    accs.setAddressInfo(createAccountRequest.getAddressInfo());
	    
		accountsRepository.save(accs);
		
		return new ResponseEntity<>(new ApiResponse(true, "Account Updated Successfully!") , HttpStatus.OK);
		
      }
	
	public void deleteAccountById(long id) throws AppException {

		Accounts accs = accountsRepository.findById(id).orElseThrow(() -> new AppException("Account does not exist."));
		
		accountsRepository.delete(accs);
		
		}
	
	public Accounts getAccountById(long id) throws AppException{
		
		Accounts accs = accountsRepository.findById(id).orElseThrow(() -> new AppException("Account does not exist."));
		
		return accs;

	}
}

