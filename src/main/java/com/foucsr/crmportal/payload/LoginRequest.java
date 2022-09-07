package com.foucsr.crmportal.payload;

import javax.validation.constraints.NotBlank;

/**
 * Created by FocusR .
 */
public class LoginRequest {
	
	@NotBlank
    private String usernameOremployeeId;

	@NotBlank
    private String password;
	
    public String getUsernameOremployeeId() {
		return usernameOremployeeId;
	}

	public void setUsernameOremployeeId(String usernameOremployeeId) {
		this.usernameOremployeeId = usernameOremployeeId;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	

	
}
