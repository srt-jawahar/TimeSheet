package com.foucsr.crmportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExcelException extends RuntimeException {

	 public ExcelException(String message) {
	        super(message);
	    }

	    public ExcelException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    
}
