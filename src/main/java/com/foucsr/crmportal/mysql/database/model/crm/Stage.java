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
@Table(name = "DEALS_STAGE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"STAGE_NAME"})
})
public class Stage {

	 @Id
	 @SequenceGenerator(name = "DEALS_STAGE_SEQ", sequenceName = "DEALS_STAGE_SEQ", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEALS_STAGE_SEQ")  
	
	 @Column(name="STAGE_ID")
	 long stageId;
	 
	@Column(name="STAGE_NAME")
	String stageName;

	@Column(name="PROBABILITY")
	long probability;
	
	
}
