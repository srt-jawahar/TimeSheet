package com.foucsr.crmportal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.foucsr.crmportal.mysql.database.model.User;


@Component
public class EmailHtmlLoader {

	Logger logger = LoggerFactory.getLogger(EmailHtmlLoader.class);
	
	@Autowired
	private TemplateEngine templateEngine;

	public String getText(String templateName, String forgetPasswordContent) {

		Context context = new Context();

		String body = new String();

		if ("email/forgetpassword_template".equals(templateName)) {

			body = loadForgetPasswordText(context, templateName, forgetPasswordContent);
			
//			logger.info("***************** Email Template body *********************\n" + body);
			
		}

		return body;
	}

	private String loadForgetPasswordText(Context context, String templateName, String forgetPasswordContent) {
		
//		logger.info("***************** Email ForgetPassword Link *********************\n" + forgetPasswordContent);

		context.setVariable("password_link", forgetPasswordContent);
		String body = templateEngine.process(templateName, context);

		return body;

	}
	
	
	@SuppressWarnings("unused")
	public String sendSignInContent(User user,String password) {

		String body = new String();

		String text = new String();
		
		
		    body = body + "Dear " + user.getName();
			
		
		    body = body + "<html>"
				+ "<body>"
				+ "<p>"
				+ "The Username and Password for you is "
				+"<p>"
				+"<b>"+ " USERNAME  :  " + user.getUsername()+ "(Your Emp ID)"
				+ "<p>"
				+" PASSWORD  :  " + password
				+ "</b>"+"<p>"
				+ AppConstants.signInDetailsText
				+ "<p>"
				+ "<a href="+AppConstants.TechStep_Url+">Click here to login </a>"
				+ "<p>"
				+ "<b><h3><mark> "+"Please Reset the password once you logged in."+"</mark></h3>"
				+ "<p>"+" Please Note,"+"<br>"
				+"<pre>"+"     1. Timesheets to be submitted on the same day or before 12.00 noon of the next day."+"<br>"
				+"<pre>"+"     2. No manual adjustments possible."+"<br>"
				+"<pre>"+"     3. Timesheets approved by reporting managers will only be considered for payroll."+"<br></b>"
				+"<p>"+"Incase if you are facing any difficulties or issues please reach to the chat support provided on the application or please write to timesheet@focusrtech.com."+"<br>"
                +"<br><p>"+"Thanks,"
				+ "</body>"
				+ "</html>";
		
		
		    return body;
	}
	
	@SuppressWarnings("unused")
	public String sendUpdateContent(User user, String password, String managerName, String managerId , String designation , String role) {

		String body = new String();

		String text = new String();
		
		
		    body = body + "Dear " + user.getName();
			
		
		    body = body + "<html>"
		    		+ "<body>"
		    		+ "<p>"
		    		+ " Your techstep hub details updated. "
		    		+ "<p>"
		    		+" NAME : " + user.getName()
		    		+"<p>"
		    		+" USERNAME : " + user.getUsername()
		    		+"<p>"
		    		+" PASSWORD : " + password
		    		+"<p>"
		    		+" EMPLOYEE_ID : " + user.getEmployeeId()
		    		+"<p>"
		    		+ "EMAIL : " + user.getEmail()
		    		+ "<p>"
		    		+ "DESIGNATION : " + designation
		    		+ "<p>"
		    		+ "ROLE : " + role
		    		+ "<p>"
		    		+" MANAGER ID : " + managerId
		    		+"<p>"
		    		+" MANAGER NAME : " + managerName
		    		+ "<p>"
		    		+ " Once you Check your TechStep Hub Details "
		    		+ "<p>"
		    		+ "<a href="+AppConstants.TechStep_Url+">Click here to login </a>"
		    		+ "<p>"
		    		+ "</body>"
		    + "</html>";

		
		    return body;
	}
		
		
	
	
}