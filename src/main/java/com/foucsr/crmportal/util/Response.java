package com.foucsr.crmportal.util;

import java.util.HashMap;
import java.util.Map;

public class Response {

	private Map<String, String> responseHeader;

	private Map<String, Object> responseData;

	public Response(){
		Map<String, String> responseHeader = new HashMap<String, String>();
		Map<String, Object> responseData = new HashMap<String, Object>();
		this.setResponseHeader(responseHeader);
		this.setResponseData(responseData);
	}
	
	/**
	 * @return the responseHeader
	 */
	public Map<String, String> getResponseHeader() {
		return responseHeader;
	}

	/**
	 * @param responseHeader
	 *            the responseHeader to set
	 */
	public void setResponseHeader(Map<String, String> responseHeader) {
		this.responseHeader = responseHeader;
	}

	/**
	 * @return the responseData
	 */
	public Map<String, Object> getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData
	 *            the responseData to set
	 */
	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}

}
