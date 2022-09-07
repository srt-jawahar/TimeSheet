package com.foucsr.crmportal.mysql.database.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "CRM_ROLES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CRM_ROLE_NAME"})
})
public class CrmRoles {

	
	 @Id
	 @SequenceGenerator(name = "CRM_ROLES_SEQ", sequenceName = "CRM_ROLES_SEQ", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_ROLES_SEQ")  
	 long id;
	 
	@Column(name="CRM_ROLE_NAME")
	String crmRoleName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCrmRoleName() {
		return crmRoleName;
	}

	public void setCrmRoleName(String crmRoleName) {
		this.crmRoleName = crmRoleName;
	}
	
	
}
