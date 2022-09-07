package com.foucsr.crmportal.util;

import java.util.List;

public class Reports {

	
	private String reportId;
	
	private String url;
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the reportDescription
	 */
	public String getReportDescription() {
		return reportDescription;
	}

	/**
	 * @param reportDescription the reportDescription to set
	 */
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	/**
	 * @return the param
	 */
	public List<ReportParameter> getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(List<ReportParameter> param) {
		this.param = param;
	}

	private String reportName;
	
	private String reportDescription;
	
	private List<ReportParameter> param;
}
