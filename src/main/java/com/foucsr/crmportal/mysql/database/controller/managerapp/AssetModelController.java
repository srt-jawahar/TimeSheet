package com.foucsr.crmportal.mysql.database.controller.managerapp;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foucsr.crmportal.mysql.database.model.managerapp.AssetIssueEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetUserEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.service.MapValidationErrorService;
import com.foucsr.crmportal.mysql.database.service.managerapp.AssetModelService;

@RestController
@RequestMapping("/api/Assets/Service")
public class AssetModelController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private AssetModelService assetModelService;

	@PostMapping("/createOrUpdateAssets")
	public ResponseEntity<?> createOrUpdateAssets(HttpServletRequest http,
			@Valid @RequestBody AssetModelEntity assetModelEntity, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> assetsRepo = assetModelService.createOrUpdateAssets(assetModelEntity, http);

		return assetsRepo;
	}

	@GetMapping("/getListOfAssets")
	public ResponseEntity<?> getListOfAssets(Principal principal) {

		ResponseEntity<?> message = assetModelService.getListOfAssets();

		return message;
	}

	@GetMapping("/getAssetDetailsById")
	public ResponseEntity<?> getAssetDetailsById(HttpServletRequest http,
			@RequestParam Map<String, String> requestParams, Principal principal) {

		Long asset_id = Long.valueOf(requestParams.get("asset_id"));

		ResponseEntity<?> message = assetModelService.getAssetDetailsById(http, asset_id);

		return message;
	}

	@GetMapping("/getUserAssetDetails")
	public ResponseEntity<?> getUserAssetDetails(HttpServletRequest http,
			@RequestParam Map<String, String> requestParams, Principal principal) {

		ResponseEntity<?> message = assetModelService.getUserAssetDetails(http);

		return message;
	}

//									CREATE ASSET USER
	
	@PostMapping("/CreateAssetUser")
	public ResponseEntity<?> CreateAssetUser(HttpServletRequest http,
			@Valid @RequestBody AssetUserEntity assetUserEntity, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> assetUserRepo = assetModelService.CreateAssetUser(assetUserEntity, http);

		return assetUserRepo;
	}
//								             APPLY ISSUE ASSET
	
	@PostMapping("/applyIssueAsset")
	public ResponseEntity<?> applyIssueAsset(HttpServletRequest http,
			@Valid @RequestBody AssetIssueEntity assetIssueEntity, BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> assetIssueResp = assetModelService.applyIssueAsset(assetIssueEntity, http);

		return assetIssueResp;
	}
//                                GET PENDING ISSUE ASSET FOR APPROVAL

	@GetMapping("/getPendingIssueAssetForApproval")
	public ResponseEntity<?> getPendingIssueAssetForApproval(HttpServletRequest http, Principal principal) {

		ResponseEntity<?> message = assetModelService.getPendingIssueAssetForApproval(http);

		return message;
	}
	
// 								  APPROVE OR REJECT ISSUE ASSET
	
	@PutMapping("/approveOrRejectIssueAsset")
	public ResponseEntity<?> bulkApprove(HttpServletRequest request, @Valid @RequestBody List<AssetIssueEntity> lines,
			BindingResult result, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ResponseEntity<?> message = assetModelService.approveOrRejectIssueAsset(lines, request);

		return message;
	}

}
