package com.foucsr.crmportal.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.timesheet.TimesheetService;

@Component
public class MailContentUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(TimesheetService.class);
	

	public void sendRejectedNotification(TimeSheetEntity timeSheet, UserRoleRepository userRoleRepository, UserRepository userRepository) {

		LOGGER.info("Timesheet Process :: sendRejectedNotification Method :: started");

		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
		String tsDate = format.format(timeSheet.getDate());

		String empMailId = "";
		String managerName = "";
		String empName = "";

		User user = null;
		UserRoleEntity userRole = null;
		Optional<User> userOpt = userRepository.findByEmployeeId(timeSheet.getEmpId());
		if (userOpt.isPresent()) {
			user = userOpt.get();
		}
		
		Optional<UserRoleEntity> usrRoleEntity = userRoleRepository.findByEmpId(user.getEmployeeId());
		if (usrRoleEntity.isPresent()) {
			userRole = usrRoleEntity.get();
		}
		
			empMailId = user.getEmail();
			empName = user.getName();
			
			User manager = userRepository.findByEmployeeId(userRole.getManagerId())
					.orElseThrow(() -> new AppException("User details does not exist."));

		
			managerName = manager.getName();


		LOGGER.info("Timesheet Process :: sendNotification Method :: started");

		Map<String, Object> requestData = new HashMap<String, Object>();
		String mailTo = empMailId;// + "," + managerMailId;

		String mailSubject = " Additional Information required for Timesheet request " + tsDate;

		String content = "<br>Dear " + empName + ",<br> " + " Your Manager " + managerName
				+ " has requested for additional information regading Timesheet request for  : " + tsDate
				+ ". <br> Please login to Timesheet Application for more details"

				+ "<br><br><br>" + "Please send mail to timesheet@focusrtech.com is case of any issues" + "<br><br>"
				+ "Thanks <br> Timesheet Application team";
		requestData.put("to", mailTo);
		requestData.put("mailSubject", mailSubject);
		requestData.put("mailContent", content);
		requestData.put("attachment", false);

		sendMail(requestData);

		LOGGER.info("Timesheet Process :: sendRejectedNotification Method :: Completed");
	}
	
	public Response sendMail(Map<String, Object> requestData) {

		String toMail = (String) requestData.get("to");
		String subject = (String) requestData.get("mailSubject");
		String content = (String) requestData.get("mailContent");
		boolean isAttachment = (boolean) requestData.get("attachment");

		String[] to = toMail.split(",");
		sendMail(to, subject, content, isAttachment);
		return null;
	}
	
	 
		/*public String send(String[] to, String Subject, String messageStr, boolean isAttachment) throws MessagingException {

		    String fromMailId = "timesheet@focusrtech.com";
		    String ccMail = "muthuganesh.b@focusrtech.com";
		 
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper;

			helper = new MimeMessageHelper(message, true);

			helper.setFrom(fromMailId);
			helper.setSubject(Subject);
			helper.setTo(to);
			helper.setCc(ccMail);
			helper.setText(messageStr, true);
			
			
			mailSender.send(message);
			return "false";
		}*/
	 
		public void sendMail(String[] toMail, String Subject, String messageStr, boolean isAttachment) {
			
			try {
				
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			
		    MimeMessage mail = mailSender.createMimeMessage();
		    
		    Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", 587);
			prop.put("mail.smtp.host", "smtpout.secureserver.net");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.debug", "true");
			prop.put("mail.smtp.ssl.trust", "smtpout.secureserver.net");
			
		    mailSender.setJavaMailProperties(prop);
		    
		    mailSender.setUsername("timesheet@focusrtech.com");
		    mailSender.setPassword("**********");
		    
		    
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	 
	        // to mails
	        InternetAddress[] mailAddress_TO = new InternetAddress[toMail.length];

			for (int i = 0; i < toMail.length; i++) {
				mailAddress_TO[i] = new InternetAddress(toMail[i]);
			}
			
			helper.setTo(mailAddress_TO);
//			helper.setCc("muthuganesh.b@focusrtech.com");
			helper.setBcc("muthuganesh.b@focusrtech.com");

			// from mail
	       helper.setFrom("timesheet@focusrtech.com");
	       
	       
	       // subject
	       helper.setSubject(Subject);
	       helper.setText(messageStr, true);
	       
	       mailSender.send(mail);
	       
	       
			} catch(Exception ex) {
				
				ex.printStackTrace();
				throw new AppException("Unable to send mail");
			}

		}

}
