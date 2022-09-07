package com.foucsr.crmportal.util;

import java.util.HashMap;
import java.util.Map;

public enum TimesheetStatus {

	
	DRAFT("DRAFT"), 
	SUBMITTED("SUBMITTED"),
	APPROVED("APPROVED"), 
	PENDING("PENDING"),
	REJECTED("REJECTED");

	public final String code;

	private static final Map<String, TimesheetStatus> STRING_TO_ENUM = new HashMap<>();

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	private TimesheetStatus(String aCode) {
		code = aCode;
	}

	public static TimesheetStatus fromString(String aCode) {
		return STRING_TO_ENUM.get(aCode);
	}

	static {
		for (TimesheetStatus name : TimesheetStatus.values()) {
			STRING_TO_ENUM.put(name.getCode(), name);
		}
	}
}
