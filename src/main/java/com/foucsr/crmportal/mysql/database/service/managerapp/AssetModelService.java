package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.exception.AppException;
import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetIssueEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetUserEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.AssetIssueRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.AssetModelRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.AssetUserRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.payload.timesheet.ManagerPendingApprovalIssueAsset;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.AppConstants;
import com.foucsr.crmportal.util.AssetIssueStatus;
import com.foucsr.crmportal.util.SCAUtil;

@Service
public class AssetModelService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AssetModelService.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private AssetModelRepository assetModelRepo;

	@Autowired
	private AssetUserRepository assetUserRepo;

	@Autowired
	private AssetIssueRepository assetIssueRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	Logger log = LoggerFactory.getLogger(AssetModelService.class);

	public ResponseEntity<?> createOrUpdateAssets(AssetModelEntity assetModelEntity, HttpServletRequest http) {

		SCAUtil sca = new SCAUtil();

		try {

			Long id = assetModelEntity.getAsset_id();

			String value = "";

			if (assetModelEntity.getAsset_category() == null)
				value = "asset_category";

			if (assetModelEntity.getMake() == null)
				value = "make";

			if (assetModelEntity.getModel() == null)
				value = "model";

			if (assetModelEntity.getAsset_serial_no() == null)
				value = "asset_serial_no";

			if (assetModelEntity.getBattery_serial_no() == null)
				value = "battery_serial_no";

			if (assetModelEntity.getProduct_id() == null)
				value = "product_id";

			if (assetModelEntity.getOs_version() == null)
				value = "os_version";

			if (assetModelEntity.getOs_key() == null)
				value = "os_key";

			if (assetModelEntity.getOs_type() == null)
				value = "os_type";

			if (assetModelEntity.getRam() == null)
				value = "ram";

			if (assetModelEntity.getDisplay_size() == null)
				value = "display_size";

			if (assetModelEntity.getStorage() == null)
				value = "storage";

			if (assetModelEntity.getProblem() == null)
				value = "problem";

			if (assetModelEntity.getConditions() == null)
				value = "conditions";

			if (assetModelEntity.getValue_of_asset() == null)
				value = "value_of_asset";

			if (assetModelEntity.getStart_date() == null)
				value = "start_date";

			if (assetModelEntity.getEnd_date() == null)
				value = "end_date";

			if (assetModelEntity.getRemarks() == null)
				value = "remarks";

			if (!"".equals(value)) {

				return new ResponseEntity(new ApiResponse(false, "Should fill the mandatory field: " + value),
						HttpStatus.BAD_REQUEST);
			}

//				                   			 WHILE UPDATE CHECK THE ACTIVE STATUS

			if (assetModelEntity != null) {

//          AssetsEntity orgLeaveMaster = assetsRepo.findAll()(assetsEntity);

				List<AssetModelEntity> updateAssets = assetModelRepo.findAll();

				if (updateAssets == null) {

					return new ResponseEntity(new ApiResponse(false, "No Assets Details!"), HttpStatus.BAD_REQUEST);

				}
			}

			assetModelRepo.save(assetModelEntity);

		} catch (Exception e) {

			log.error("***************** Unable to save Assets Details!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to save Assets Details!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(assetModelEntity, HttpStatus.CREATED);
	}

//							                     		GET LIST OF ASSETS

	public ResponseEntity<?> getListOfAssets() {

		List<AssetModelEntity> listOfAssets = null;
		SCAUtil sca = new SCAUtil();

		try {

			listOfAssets = assetModelRepo.findAll();

			if (listOfAssets == null) {

				listOfAssets = new ArrayList<AssetModelEntity>();
			}

		} catch (Exception e) {
			log.error("***************** Unable to get List Of All Assets! *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get List Of All Assets!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(listOfAssets, HttpStatus.OK);

	}

	public ResponseEntity<?> getAssetDetailsById(HttpServletRequest http, Long asset_id) {

		SCAUtil sca = new SCAUtil();

		List<AssetModelEntity> assets = null;

		try {

			assets = assetModelRepo.getAssetDetailsById(asset_id);

			if (assets == null) {

				assets = new ArrayList<AssetModelEntity>();
			}

		} catch (Exception e) {

			log.error("***************** Unable to get assets!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get assets!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(assets, HttpStatus.OK);

	}

	public ResponseEntity<?> getUserAssetDetails(HttpServletRequest http) {

		SCAUtil scaU = new SCAUtil();

		List<AssetModelEntity> assetModelEntity = new ArrayList<AssetModelEntity>();

		try {

			Long userId = scaU.getUserIdFromRequest(http, tokenProvider);

			assetModelEntity = assetModelRepo.getUserAssetDetails(userId);

			assetModelRepo.saveAll(assetModelEntity);

		}

		catch (Exception e) {

			log.error("***************** Unable to Get User Id  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to Get User Id!" + msg), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(assetModelEntity, HttpStatus.OK);
	}

	public ResponseEntity<?> CreateAssetUser(AssetUserEntity assetUserEntity, HttpServletRequest http) {

		SCAUtil scaU = new SCAUtil();

		AssetModelEntity assetModelEntity = new AssetModelEntity();

		try {

			Long assetid = assetUserEntity.getAsset_id();

			Optional<AssetModelEntity> opt = assetModelRepo.findById(assetid);

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, " Asset Id is Not Existing !"),
						HttpStatus.BAD_REQUEST);
			}

			AssetUserEntity latestAssetUser = assetUserRepo.getLatestAssetUserDetails(assetid);

			if (latestAssetUser != null) {

				Date end_date = latestAssetUser.getEnd_date();

				if (end_date == null) {
					return new ResponseEntity(
							new ApiResponse(false,
									" Asset is already assigned.  WE Can't Assign This Asset To New User !"),
							HttpStatus.BAD_REQUEST);
				}

				Date date = Calendar.getInstance().getTime();

				int value = date.compareTo(end_date);

				if (value == 0) {

					return new ResponseEntity(
							new ApiResponse(false,
									" Asset is already assigned.  WE Can't Assign This Asset To New User !"),
							HttpStatus.BAD_REQUEST);
				}

				else if (value < 0) {

					return new ResponseEntity(
							new ApiResponse(false,
									"Asset is already assigned.  WE Can't Assign This Asset To New User !"),
							HttpStatus.BAD_REQUEST);
				}
			}

			Long id = assetUserEntity.getAsset_user_id();

			String value = "";

			if (assetUserEntity.getAsset_id() == null)
				value = "asset_id";

			if (assetUserEntity.getStart_date() == null)
				value = "start_date";

			if (assetUserEntity.getUser_id() == null)
				value = "user_id";

			if (!"".equals(value)) {

				return new ResponseEntity(new ApiResponse(false, "Should fill the mandatory field: " + value),
						HttpStatus.BAD_REQUEST);
			}

			assetUserRepo.save(assetUserEntity);

		} catch (Exception e) {

			log.error("***************** Unable to get User Asset list!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get User Asset list!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(assetUserEntity, HttpStatus.OK);

	}

//	  							     			APPLY ISSUE ASSET

	public ResponseEntity<?> applyIssueAsset(AssetIssueEntity assetIssueEntity, HttpServletRequest http) {

		SCAUtil sca = new SCAUtil();
		AssetIssueEntity entity = new AssetIssueEntity();

		try {
			
			Long userId = sca.getUserIdFromRequest(http, tokenProvider);

			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "User is not found !"), HttpStatus.BAD_REQUEST);
			}

			user = opt.get();

			AssetIssueStatus status = AssetIssueStatus.SUBMITTED;

			entity.setApprover(userService.getManager(user.getEmployeeId()));
			entity.setAsset_issue_id(assetIssueEntity.getAsset_issue_id());
			entity.setAsset_id(assetIssueEntity.getAsset_id());
			entity.setEmpId(assetIssueEntity.getEmpId());
			entity.setSubmittedOn(LocalDateTime.now());
			entity.setApprovedOn(assetIssueEntity.getApprovedOn());
			entity.setReason(assetIssueEntity.getReason());
			entity.setStatus(status.getCode());

			assetIssueRepo.save(entity);

		}

		catch (Exception e) {

			log.error("***************** Unable to apply issue asset!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to apply issue asset!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(entity, HttpStatus.CREATED);
	}

//	                GET PENDING ISSUE ASSET FOR APPROVAL

	public ResponseEntity<?> getPendingIssueAssetForApproval(HttpServletRequest http) {

		SCAUtil scaU = new SCAUtil();
		List<AssetIssueEntity> pendingAssets = new ArrayList<AssetIssueEntity>();

		List<ManagerPendingApprovalIssueAsset> managerApproval = new ArrayList<ManagerPendingApprovalIssueAsset>();

		try {

			Long userId = scaU.getUserIdFromRequest(http, tokenProvider);

			Optional<User> opt = userRepository.findUser(userId);

			User user = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "Approval User is not found !"),
						HttpStatus.BAD_REQUEST);
			}

			user = opt.get();

			String manager_id = user.getEmployeeId();

			pendingAssets = assetIssueRepo.getPendingIssueAssetForApproval(manager_id);

			for (AssetIssueEntity assetIssueEntity : pendingAssets) {

				System.out.println(assetIssueEntity.getEmpId());

				User user1 = userRepository.findByEmployeeId(assetIssueEntity.getEmpId())
						.orElseThrow(() -> new AppException("User details does not exist."));

				ManagerPendingApprovalIssueAsset newProfile = new ManagerPendingApprovalIssueAsset();

				newProfile.setAsset_issue_id(assetIssueEntity.getAsset_issue_id());
				newProfile.setAsset_id(assetIssueEntity.getAsset_id());
				newProfile.setReason(assetIssueEntity.getReason());
				newProfile.setApprovedOn(assetIssueEntity.getApprovedOn());
				newProfile.setApprover(assetIssueEntity.getApprover());
				newProfile.setEmpId(assetIssueEntity.getEmpId());
				newProfile.setStatus(assetIssueEntity.getStatus());
				newProfile.setSubmittedOn(assetIssueEntity.getSubmittedOn());

				managerApproval.add(newProfile);

			}

			if (pendingAssets == null) {

				pendingAssets = new ArrayList<AssetIssueEntity>();
			}

		} catch (Exception e) {

			LOGGER.error("***************** Unable to get pending assets list!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get pending assets list!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(managerApproval, HttpStatus.OK);

	}

//										APPROVE OR REJECT ISSUE ASSET BY MANAGER 

	public ResponseEntity<?> approveOrRejectIssueAsset(List<AssetIssueEntity> assetList, HttpServletRequest request) {

		SCAUtil scaU = new SCAUtil();

		try {

			if (assetList == null || assetList.size() == 0) {

				return new ResponseEntity(new ApiResponse(false, "Please select atleast one issue asset to approve!"),
						HttpStatus.BAD_REQUEST);
			}

			String approvestatus = AssetIssueStatus.APPROVED.getCode();
			String rejectstatus = AssetIssueStatus.REJECTED.getCode();

			List<AssetIssueEntity> issueAssetAppliedList = new ArrayList<AssetIssueEntity>();

			for (AssetIssueEntity asset : assetList) {

				if (!(approvestatus.equals(asset.getStatus()) || rejectstatus.equals(asset.getStatus()))) {

					return new ResponseEntity(new ApiResponse(false, "Action or status should be APPROVED/REJECTED!"),
							HttpStatus.BAD_REQUEST);
				}

				Optional<AssetIssueEntity> tsEntity = assetIssueRepo.findById(asset.getAsset_issue_id());
				AssetIssueEntity entity = null;

				if (tsEntity.isPresent()) {

					entity = tsEntity.get();
					entity.setStatus(asset.getStatus());
					entity.setApprovedOn(LocalDateTime.now());

					issueAssetAppliedList.add(entity);
				}
			}

			assetIssueRepo.saveAll(issueAssetAppliedList);

		} catch (Exception e) {

			log.info("***************** Unable to approve/reject Issue asset!  *********************\n" + e);

			String msg = scaU.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to approve/Issue asset!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, AppConstants.Success_Message), HttpStatus.OK);
	}

}
