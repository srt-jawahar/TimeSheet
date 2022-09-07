package com.foucsr.crmportal.mysql.database.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FILES_INFO")
public class FilesInfo {
	
	
	@Id
	 @SequenceGenerator(name = "FILES_INFO_SEQ", sequenceName = "FILES_INFO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILES_INFO_SEQ")  
	 long id;
	
	@Column(name="FILE_NAME") 
	String fileName;

	@Column(name="ATTACHED_BY") 
	String attachedBy;
	
	@Column(name="FILE_SIZE")
	Long size;
	
	@Column(name="DATE_ADDED")
	String date;
	
	@Column(name="URL") 
	String url;
	
	@Column(name="ATTACHMENT_TYPE") 
	String fileOrUrl;
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAttachedBy() {
		return attachedBy;
	}

	public void setAttachedBy(String attachedBy) {
		this.attachedBy = attachedBy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileOrUrl() {
		return fileOrUrl;
	}

	public void setFileOrUrl(String fileOrUrl) {
		this.fileOrUrl = fileOrUrl;
	}
	
	
	
	
	
	
	
	

}
