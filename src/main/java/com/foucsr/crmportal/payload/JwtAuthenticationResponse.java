package com.foucsr.crmportal.payload;

/**
 * Created by FocusR.
 */
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    
    private String name;
    
    private String designation;
    
    private String emailId;
    
    private String is_bulk_upload;
    
    private Integer date_freeze;
    
    private String is_laptop_details_submitted;
    
    

    public String getIs_bulk_upload() {
		return is_bulk_upload;
	}

	public void setIs_bulk_upload(String is_bulk_upload) {
		this.is_bulk_upload = is_bulk_upload;
	}

	
	public Integer getDate_freeze() {
		return date_freeze;
	}

	public void setDate_freeze(Integer date_freeze) {
		this.date_freeze = date_freeze;
	}

	public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIs_laptop_details_submitted() {
		return is_laptop_details_submitted;
	}

	public void setIs_laptop_details_submitted(String is_laptop_details_submitted) {
		this.is_laptop_details_submitted = is_laptop_details_submitted;
	}

	
}
