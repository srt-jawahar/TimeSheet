package com.foucsr.crmportal.util;

import java.util.HashMap;
import java.util.Map;

public class Request {
	
	private Map<String,String> requestHeader;
	
	private Map<String,Object> requestData;
	
	public Request(){
		Map<String,String> requestHeader = new HashMap<String,String>();
		Map<String,Object> requestData = new HashMap<String,Object>();
		
		this.setRequestHeader(requestHeader);
		this.setRequestData(requestData);
	}

	public Map<String,String> getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(Map<String,String> requestHeader) {
		this.requestHeader = requestHeader;
	}

	public Map<String,Object> getRequestData() {
		return requestData;
	}

	public void setRequestData(Map<String,Object> requestData) {
		this.requestData = requestData;
	}

	

}
