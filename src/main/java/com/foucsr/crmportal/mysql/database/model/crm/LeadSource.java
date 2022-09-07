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
@Table(name = "LEAD_SOURCE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LEAD_SOURCE_NAME"})
})
public class LeadSource {

	 @Id
	 @SequenceGenerator(name = "LEAD_SOURCE_SEQ", sequenceName = "LEAD_SOURCE_SEQ", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEAD_SOURCE_SEQ")  
	
	 @Column(name="LEAD_SOURCE_ID")
	 long leadSourceId;
	 
	@Column(name="LEAD_SOURCE_NAME")
	String leadSourceName;
	
	
	
}
