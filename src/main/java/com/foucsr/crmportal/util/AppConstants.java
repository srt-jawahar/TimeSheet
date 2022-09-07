package com.foucsr.crmportal.util;

public interface AppConstants {
	String DEFAULT_PAGE_NUMBER = "0";
	String DEFAULT_PAGE_SIZE = "30";

	int MAX_PAGE_SIZE = 50;

	long ASN_ID = 1;

	String forgetPasswordSubject = "Password change Link";

	String forgetPasswordText = "To reset your password, click the link below.\n"
			+ "This change password link will become invalid after 7 days.\n\n";

	String forgetPasswordTemplate = "email/forgetpassword_template";

	String Success_Message = "Success";
	
//	String App_Url = "http://secure.focusrtech.com:3030/timesheet/";

  String App_Url = "http://timesheet.focusrtech.com:8025/timesheet/";
	
	String TechStep_Url = "http://techstephub.focusrtech.com:3030/techstep/auth/login";
	
	String SMTP_Host = "173.201.193.228";
	
	String SMTP_Port = "25";
	
	String SMTP_Username = "timesheet@focusrtech.com";
	
	String SMTP_Password = "Welcome123";
	
	String signInDetailsText = "To Login to your account, click the link below.\n";
	
	String signInDetailsSubject = "SignIn Details";
	
	String MAIL_SUBJECT = "Timesheet details updated. ";


}
