package com.foucsr.crmportal.mysql.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "APPLICATIONS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"APP_NAME"})
})
public class Applications {

	
	 @Id
	 @SequenceGenerator(name = "APPLICATIONS_SEQ", sequenceName = "APPLICATIONS_SEQ", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATIONS_SEQ")  
	 long id;
	 
	 @Column(name="APP_NAME")
	 String appName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	 
	 
	 
}
