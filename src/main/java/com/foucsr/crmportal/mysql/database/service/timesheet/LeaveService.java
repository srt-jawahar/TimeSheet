package com.foucsr.crmportal.mysql.database.service.timesheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.Role;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.CountryEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveBalanceEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.CountryRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.LeaveAppliedRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.LeaveBalanceRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.LeaveMasterRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalLeave;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalTimesheet;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.LeaveAssignUserRequest;
import com.foucsr.crmportal.util.SCAUtil;
import com.foucsr.crmportal.util.TimesheetStatus;

@Service
public class LeaveService {

	@Autowired
	private LeaveMasterRepository leaveMasterRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LeaveAppliedRepository leaveAppliedRepository;

	Logger log = LoggerFactory.getLogger(LeaveService.class);

	public ResponseEntity<?> createOrUpdateLeaveMaster(LeaveMasterEntity leaveMaster, HttpServletRequest http) {

		SCAUtil sca = new SCAUtil();

		try {

			Long leave_master_id = leaveMaster.getLeave_master_id();

			String value = "";

			if (leaveMaster.getCategory_name() == null)
				value = "Category";
			if (leaveMaster.getCountry_code() == null)
				value = "Country Code";
			if (leaveMaster.getCountry_name() == null)
				value = "Country Name";
			if (leaveMaster.getTotal_leave() == null)
				value = "Total leave";
			if (leaveMaster.getIs_doc_required() == null)
				value = "Doc required";
			if (leaveMaster.getIs_remarks_required() == null)
				value = "Remarks required";
			if (leaveMaster.getIs_active() == null)
				value = "Active";

			if (!"".equals(value)) {

				return new ResponseEntity(new ApiResponse(false, "Should fill the mandatory field: " + value),
						HttpStatus.BAD_REQUEST);
			}

			if (!"Y".equals(leaveMaster.getIs_active()) && !"N".equals(leaveMaster.getIs_active())) {

				return new ResponseEntity(new ApiResponse(false, "Activeness should Y/N type"), HttpStatus.BAD_REQUEST);
			}

			// while update check the active status
			if (leave_master_id != null) {

				LeaveMasterEntity orgLeaveMaster = leaveMasterRepository.findLeaveMasterById(leave_master_id);

				if (orgLeaveMaster == null) {

					return new ResponseEntity(new ApiResponse(false, "No leave Master!"), HttpStatus.BAD_REQUEST);

				}
			}

			leaveMasterRepository.save(leaveMaster);

		} catch (Exception e) {

			log.error("***************** Unable to save leave master!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to save leave master!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(leaveMaster, HttpStatus.CREATED);
	}

	public ResponseEntity<?> getCountries() {

		List<CountryEntity> countries = null;
		SCAUtil sca = new SCAUtil();

		try {

			countries = countryRepository.findAll();

			if (countries == null) {

				countries = new ArrayList<CountryEntity>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get countries!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get countries!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(countries, HttpStatus.OK);

	}

	public ResponseEntity<?> assignLeaveCategoryToUser(List<LeaveAssignUserRequest> usersCountries,
			HttpServletRequest http) {

		List<LeaveBalanceEntity> leaveBalanceList = new ArrayList<LeaveBalanceEntity>();
		SCAUtil sca = new SCAUtil();

		try {

			Date date = new Date();
			String year = String.valueOf(date.getYear() + 1900);
			
			for (LeaveAssignUserRequest assign : usersCountries) {

				String countryCode = assign.getCountry_code();

				List<LeaveMasterEntity> leaveMasters = leaveMasterRepository.findLeaveMasterByCountry(countryCode);

				if (leaveMasters.isEmpty()) {

					return new ResponseEntity(
							new ApiResponse(false, "No Leave Master for this country : " + countryCode),
							HttpStatus.BAD_REQUEST);
				}

				for (LeaveMasterEntity leave : leaveMasters) {

					LeaveBalanceEntity leaveBalance = new LeaveBalanceEntity();
					leaveBalance.setUser_id(assign.getUser_id());
					leaveBalance.setCountry_code(leave.getCountry_code());
					leaveBalance.setCategory_name(leave.getCategory_name());
					leaveBalance.setBalance_leave(leave.getTotal_leave());
					leaveBalance.setLeave_master_id(leave.getLeave_master_id());
					leaveBalance.setYear(year);
					leaveBalance.setTotal_leave(leave.getTotal_leave());

					leaveBalanceList.add(leaveBalance);
				}

			}

			leaveBalanceRepository.saveAll(leaveBalanceList);

		} catch (Exception e) {

			log.error("***************** Unable to assign leave to user!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to assign leave to user!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(leaveBalanceList, HttpStatus.CREATED);
	}

	public ResponseEntity<?> applyLeave(LeaveAppliedEntity leaveApply, HttpServletRequest http) {

		SCAUtil sca = new SCAUtil();
		LeaveAppliedEntity entity = new LeaveAppliedEntity();
		Date from_date = null;
		Date to_date = null;

		try {
			

			
			DateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
			String year = "";
			
			
			try {
				
				LeaveAppliedEntity leaveEntity = leaveAppliedRepository.getLeaveByDate(leaveApply.getFrom_date(), leaveApply.getTo_date());
				
				if(leaveEntity != null) {
					
					return new ResponseEntity(new ApiResponse(false, "Applied already to this from/to date"),
							HttpStatus.BAD_REQUEST);
				}
				
			  from_date = formatter.parse(leaveApply.getFrom_date());
			  to_date = formatter.parse(leaveApply.getTo_date());
			  year = String.valueOf(from_date.getYear() + 1900);
			  
			}
			catch(Exception e) {
				
				return new ResponseEntity(new ApiResponse(false, "Date format error! Format is 'yyyy-MMM-dd' "),
						HttpStatus.BAD_REQUEST);
			}
			
			Long userId = sca.getUserIdFromRequest(http, tokenProvider);
			
			LeaveBalanceEntity leaveBalanceEntity = leaveBalanceRepository.findLeaveMasterById(userId, leaveApply.getLeave_master_id(), year);
			
			if (leaveBalanceEntity == null) {
				 
				return new ResponseEntity(new ApiResponse(false, "Applied category is not assigned : " ),
						HttpStatus.BAD_REQUEST);
	        }	
			
			Double balanceLeave = leaveBalanceEntity.getBalance_leave();
			
			Double totalNoOfLeaveDays = calculateLeaveDays(leaveApply, from_date, to_date);
			
			if (Double.compare(balanceLeave, totalNoOfLeaveDays) < 0) {
	 
				return new ResponseEntity(new ApiResponse(false, "Applied days should lesser than balance : " + balanceLeave),
						HttpStatus.BAD_REQUEST);
	        }	       
			
			if(totalNoOfLeaveDays <= 0) {
				
				return new ResponseEntity(new ApiResponse(false, "Applied days should greater than zero"),
						HttpStatus.BAD_REQUEST);
			}

			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "User is not found !"), HttpStatus.BAD_REQUEST);
			}

			user = opt.get();

			TimesheetStatus status = TimesheetStatus.SUBMITTED;

			entity.setStatus(status.getCode());
			entity.setUserComments(leaveApply.getUserComments());
			entity.setSubmittedOn(LocalDateTime.now());
			entity.setFrom_date(leaveApply.getFrom_date());
			entity.setTo_date(leaveApply.getTo_date());
			entity.setApprover(userService.getManager(user.getEmployeeId()));
			entity.setEmpId(user.getEmployeeId());
			entity.setFrom_first_half(leaveApply.getFrom_first_half());
			entity.setTo_first_half(leaveApply.getTo_first_half());
			entity.setFrom_second_half(leaveApply.getFrom_second_half());
			entity.setTo_second_half(leaveApply.getTo_second_half());
			entity.setMobile_num(leaveApply.getMobile_num());
			entity.setUser_id(userId);
			entity.setApplied_days(totalNoOfLeaveDays);
			entity.setCategory_name(leaveApply.getCategory_name());
			entity.setCountry_code(leaveApply.getCountry_code());
			entity.setLeave_master_id(leaveApply.getLeave_master_id());

			leaveAppliedRepository.save(entity);
			
			balanceLeave = balanceLeave - totalNoOfLeaveDays;
			leaveBalanceEntity.setBalance_leave(balanceLeave);
			
			leaveBalanceRepository.save(leaveBalanceEntity);

		} catch (Exception e) {

			log.error("***************** Unable to apply leave!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to apply leave!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(entity, HttpStatus.CREATED);
	}
	
	
	private Double calculateLeaveDays(LeaveAppliedEntity leaveApply, Date from_date, Date to_date) {

		Double totalNoOfLeaveDays = 0.0;
		Double halfDays = 0.0;
		long diff = to_date.getTime() - from_date.getTime();
		long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		totalNoOfLeaveDays = (diffDays < 0) ? 0.0 : diffDays + 1.0; 
		
		if("Y".equals(leaveApply.getFrom_first_half()) 
				|| "Y".equals(leaveApply.getFrom_second_half())){
			
			halfDays = halfDays + 0.5;
		}
		
		if("Y".equals(leaveApply.getTo_first_half()) 
				|| "Y".equals(leaveApply.getTo_second_half())){
			
			halfDays = halfDays + 0.5;
		}
		
		totalNoOfLeaveDays = totalNoOfLeaveDays - halfDays;
		
		return totalNoOfLeaveDays;
	}
	
	
	public ResponseEntity<?> getDataToApplyLeave(HttpServletRequest http) {

		List<LeaveBalanceEntity> leaveBalanceList = null;
		SCAUtil sca = new SCAUtil();

		try {

			Date date = new Date();

			String year = String.valueOf(date.getYear() + 1900);

			Long userId = sca.getUserIdFromRequest(http, tokenProvider);

			leaveBalanceList = leaveBalanceRepository.getYearlyUserLeaveBalance(userId, year);

			if (leaveBalanceList == null) {

				leaveBalanceList = new ArrayList<LeaveBalanceEntity>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get leave balance list!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get leave balance list!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(leaveBalanceList, HttpStatus.OK);

	}
	
	public ResponseEntity<?> getPendingLeaveForApproval(HttpServletRequest http, String value) {

		SCAUtil scaU = new SCAUtil();
		List<LeaveAppliedEntity> pendingLeaveApprovals = new ArrayList<LeaveAppliedEntity>();
		List<ManagerPendingApprovalLeave> managerApproval = new ArrayList<ManagerPendingApprovalLeave>();

		try {
			
			if(value == null 
					|| "".equals(value)) {
				
				return new ResponseEntity(new ApiResponse(false, "Please pass the 'value' query param manager/user "),
						HttpStatus.BAD_REQUEST);
			}

			Long userId = scaU.getUserIdFromRequest(http, tokenProvider);

			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "Approval User is not found !"),
						HttpStatus.BAD_REQUEST);
			}
			
			user = opt.get();

			
			if ("manager".equalsIgnoreCase(value)) {

				String manager_id = user.getEmployeeId();
				
				pendingLeaveApprovals = leaveAppliedRepository.getPendingLeaveForApproval(manager_id);
				
			} else {
				
                String emp_id = user.getEmployeeId();
				
				pendingLeaveApprovals = leaveAppliedRepository.getAllAppliedLeaves(emp_id);
			}


			for (LeaveAppliedEntity leaveAppliedEntity : pendingLeaveApprovals) {

				User user1 = userRepository.findByEmployeeId(leaveAppliedEntity.getEmpId())
						.orElseThrow(() -> new AppException("User details does not exist."));
				ManagerPendingApprovalLeave newProfile = new ManagerPendingApprovalLeave();
				newProfile.setName(user1.getName());
				newProfile.setApprovedOn(leaveAppliedEntity.getApprovedOn());
				newProfile.setApprover(leaveAppliedEntity.getApprover());

				newProfile.setEmpId(leaveAppliedEntity.getEmpId());
				newProfile.setLeave_applied_id(leaveAppliedEntity.getLeave_applied_id());
				newProfile.setManagerComments(leaveAppliedEntity.getManagerComments());
				newProfile.setStatus(leaveAppliedEntity.getStatus());
				newProfile.setSubmittedOn(leaveAppliedEntity.getSubmittedOn());
				newProfile.setUserComments(leaveAppliedEntity.getUserComments());
				newProfile.setFrom_date(leaveAppliedEntity.getFrom_date());
				newProfile.setTo_date(leaveAppliedEntity.getTo_date());

				managerApproval.add(newProfile);

			}

			if (pendingLeaveApprovals == null) {

				pendingLeaveApprovals = new ArrayList<LeaveAppliedEntity>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get pending Leave Approval list!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get pending Leave Approval list!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(managerApproval, HttpStatus.OK);

	}
	
	
	public ResponseEntity<?> approveOrRejectLeave(List<LeaveAppliedEntity> leaveList, HttpServletRequest request) {

		SCAUtil scaU = new SCAUtil();
		
		try {
			
			if (leaveList == null || leaveList.size() == 0) {
				
				return new ResponseEntity(new ApiResponse(false, "Please select atleast one Leave to approve!"),
						HttpStatus.BAD_REQUEST);
			}

			String approvestatus = TimesheetStatus.APPROVED.getCode();
			String rejectstatus = TimesheetStatus.REJECTED.getCode();
			
			List<LeaveAppliedEntity> leaveAppliedList = new ArrayList<LeaveAppliedEntity>();
			
			for(LeaveAppliedEntity leave : leaveList) {
				
				if( !(approvestatus.equals(leave.getStatus() )
						|| rejectstatus.equals(leave.getStatus())) ){
					
					return new ResponseEntity(new ApiResponse(false, "Action or status should be APPROVED/REJECTED!"), 
							HttpStatus.BAD_REQUEST);
				}

				Optional<LeaveAppliedEntity> tsEntity = leaveAppliedRepository.findById(leave.getLeave_applied_id());
				LeaveAppliedEntity entity = null;
				
				if (tsEntity.isPresent()) {
					
					
					entity = tsEntity.get();
					entity.setStatus(leave.getStatus());
					
					if(leave.getManagerComments() != null) {
						
						entity.setManagerComments(leave.getManagerComments());
					}
					
					entity.setApprovedOn(LocalDateTime.now());
					
					leaveAppliedList.add(entity);
					
				}
			}
			
			leaveAppliedRepository.saveAll(leaveAppliedList);
									

		} catch (Exception e) {

			log.info("***************** Unable to approve/reject Leave!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to approve/reject Leave!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> getLeaveMaster() {

		List<LeaveMasterEntity> countries = null;
		SCAUtil sca = new SCAUtil();

		try {

			countries = leaveMasterRepository.findAll();

			if (countries == null) {

				countries = new ArrayList<LeaveMasterEntity>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get leave master!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get leave master!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(countries, HttpStatus.OK);

	}





}
