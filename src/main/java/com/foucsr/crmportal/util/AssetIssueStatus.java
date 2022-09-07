package com.foucsr.crmportal.util;

import java.util.HashMap;
import java.util.Map;

public enum AssetIssueStatus {
	
	DRAFT("DRAFT"), 
	SUBMITTED("SUBMITTED"),
	APPROVED("APPROVED"), 
	PENDING("PENDING"),
	REJECTED("REJECTED");

	public final String code;

	private static final Map<String, AssetIssueStatus> STRING_TO_ENUM = new HashMap<>();

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	private AssetIssueStatus(String aCode) {
		code = aCode;
	}

	public static AssetIssueStatus fromString(String aCode) {
		return STRING_TO_ENUM.get(aCode);
	}

	static {
		for (AssetIssueStatus name : AssetIssueStatus.values()) {
			STRING_TO_ENUM.put(name.getCode(), name);
		}
	}
	
	
}

