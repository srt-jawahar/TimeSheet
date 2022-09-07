package com.foucsr.crmportal.mysql.database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String homeDefault(ModelMap modelMap) {

		return "index";
	}
	

	@RequestMapping(value = "/index")
	public String homePage(ModelMap modelMap) {

		return "index";
	}
		
	@RequestMapping(value = "/resetPwd/{token}")
	public String forgetPassword(ModelMap modelMap) {

		return "index";
	}
	
	
	@RequestMapping(value = "/auth")
	public String auth(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/500")
	public String _500(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/metrics")
	public String dashboardMetrics(ModelMap modelMap) {

		return "index";
	}
	
	
	
	@RequestMapping(value = "/dashboard/admin")
	public String dashboardAdmin(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/changePassword")
	public String dashboardChangePassword(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/admin/user-management")
	public String dashboardAdminUserManagement(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/admin/user-management/user-create")
	public String dashboardAdminUserManagementUserCreate(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/admin/user-management/user-edit")
	public String dashboardAdminUserManagementUserEdit(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/admin/user-management/user-bulkupload")
	public String dashboardAdminUserManagementUserBulkUpload(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet")
	public String dashboardTimesheet(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-entry")
	public String dashboardTimesheetEntry(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/add-timesheet")
	public String dashboardTimesheetAddTimesheet(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/edit-timesheet")
	public String dashboardTimesheetEditTimesheet(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/reports")
	public String dashboardTimesheetReports(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/approval")
	public String dashboardTimesheetApproval(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/approvalList")
	public String dashboardTimesheetApprovalList(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-manager-leave-approval")
	public String dashboardTimesheetManagerLeaveApproval(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-manager-leave-application")
	public String dashboardTimesheetManagerLeaveApplication(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-settings")
	public String dashboardTimesheetSettings(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-self-rating")
	public String dashboardTimesheetKpiKraSelfRating(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-manager-rating")
	public String dashboardTimesheetKpiKraManagerRating(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-user-rating-list")
	public String dashboardTimesheetKpiKraUserRatingList(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-config")
	public String dashboardTimesheetKpiKraConfig(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-config/create")
	public String dashboardTimesheetKpiKraConfigCreate(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-config/edit")
	public String dashboardTimesheetKpiKraConfigEdit(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-master")
	public String dashboardTimesheetKpiKraMaster(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-master/create")
	public String dashboardTimesheetKpiKraMasterCreate(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-kpi-kra-master/edit")
	public String dashboardTimesheetKpiKraMasterEdit(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-user-settings")
	public String dashboardTimesheetUserSettings(ModelMap modelMap) {

		return "index";
	}
	
	@RequestMapping(value = "/dashboard/timesheet/timesheet-user-settings/update")
	public String dashboardTimesheetUserSettingsUpdate(ModelMap modelMap) {

		return "index";
	}
	

	@RequestMapping(value = "/auth/login")
	public String authLogin(ModelMap modelMap) {

		return "index";
	}
	
	

	@RequestMapping(value = "/auth/reset-password")
	public String authResetPassword(ModelMap modelMap) {

		return "index";
	}
	

	@RequestMapping(value = "/auth/resetpasswordpage")
	public String authResetPasswordPage(ModelMap modelMap) {

		return "index";
	}
	
	
	@RequestMapping(value = "/auth/resetpasswordpage/**")
	public String resetPwdPage(ModelMap modelMap) {

	return "index";
	}
	
	@RequestMapping(value = "/auth/resetpasswordpage/resetPwd/**")
	public String resetPwdPageToken(ModelMap modelMap) {

	return "index";
	}
	

	@RequestMapping(value = "/auth/resetpasswordpage/resetPwd/{token}")
	public String resetPwdPageResetPwdToken(ModelMap modelMap) {

	return "index";
	}

	@RequestMapping(value = "/auth/verify")
	public String authVerify(ModelMap modelMap) {

		return "index";
	}
	
	
}