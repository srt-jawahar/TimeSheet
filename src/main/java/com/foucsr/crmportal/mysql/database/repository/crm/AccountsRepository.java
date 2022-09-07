package com.foucsr.crmportal.mysql.database.repository.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foucsr.crmportal.mysql.database.model.crm.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

	@Override
    List<Accounts> findAll(); 
	
	Boolean existsByAccountName(String accountName);
	
	
	
}
