package com.foucsr.crmportal.util;

import java.util.Map;

public class ReportParameter {
	
	private String order;
	
	private String name;
	
	private String type;
	
	private Map<String,String> dropdownValue;

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dropdownValue
	 */
	public Map<String, String> getDropdownValue() {
		return dropdownValue;
	}

	/**
	 * @param dropdownValue the dropdownValue to set
	 */
	public void setDropdownValue(Map<String, String> dropdownValue) {
		this.dropdownValue = dropdownValue;
	}

}
