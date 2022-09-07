package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.managerapp.AssetModelEntity;
import com.foucsr.crmportal.mysql.database.model.managerapp.LaptopEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveMasterEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.mysql.database.repository.UserRepository;
import com.foucsr.crmportal.mysql.database.repository.managerapp.LaptopRepository;
import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.mysql.database.service.UserService;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.security.JwtTokenProvider;
import com.foucsr.crmportal.util.LaptopLOV;
import com.foucsr.crmportal.util.SCAUtil;

@Service
public class LaptopService {

	public static final Logger LOGGER = LoggerFactory.getLogger(LaptopService.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private LaptopRepository laptopRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	Logger log = LoggerFactory.getLogger(LaptopService.class);

	public ResponseEntity<?> getLaptopLOVList() {

		LaptopLOV laptopLOVList = new LaptopLOV();
		SCAUtil sca = new SCAUtil();

		try {

			laptopLOVList.setMakeList(getMakeList());
			laptopLOVList.setAssetTypeList(getAssetType());
			laptopLOVList.setOsList(getOSVersion());
			laptopLOVList.setRamList(getRAM());
			laptopLOVList.setStorageList(getStorage());
			laptopLOVList.setAntiVirusList(getAntiVirus());
			laptopLOVList.setEmailList(getEmail());
			laptopLOVList.setOutlookVersionList(getOutlookVersion());

		} catch (Exception e) {

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get LOV!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(laptopLOVList, HttpStatus.OK);

	}

	private List<String> getMakeList() {

		List<String> makeList = new ArrayList<String>();
		makeList.add("HP");
		makeList.add("Lenovo");
		makeList.add("Dell");
		makeList.add("Toshiba");

		return makeList;
	}

	private List<String> getAssetType() {

		List<String> AssetTypeList = new ArrayList<String>();
		AssetTypeList.add("New");
		AssetTypeList.add("Used");

		return AssetTypeList;
	}

	private List<String> getOSVersion() {

		List<String> osList = new ArrayList<String>();
		osList.add("Windows 7");
		osList.add("Windows 8");
		osList.add("Windows 10");
		osList.add("Windows 11");

		return osList;
	}

	private List<String> getRAM() {

		List<String> ramList = new ArrayList<String>();
		ramList.add("4 GB");
		ramList.add("8 GB");
		ramList.add("12 GB");
		ramList.add("16 GB");

		return ramList;
	}

	private List<String> getStorage() {

		List<String> storageList = new ArrayList<String>();
		storageList.add("256 GB");
		storageList.add("512 GB");
		storageList.add("1 TB");

		return storageList;
	}

	private List<String> getAntiVirus() {

		List<String> antiVirusList = new ArrayList<String>();
		antiVirusList.add("Windows Defender");
		antiVirusList.add("Bit Defender");

		return antiVirusList;
	}

	private List<String> getEmail() {

		List<String> emailList = new ArrayList<String>();
		emailList.add("Outlook");
		emailList.add("Thunderbird");
		emailList.add("Go Daddy Site");

		return emailList;
	}

	private List<String> getOutlookVersion() {

		List<String> outlookVersionList = new ArrayList<String>();
		outlookVersionList.add("2007");
		outlookVersionList.add("2010");
		outlookVersionList.add("2013");
		outlookVersionList.add("2016");
		outlookVersionList.add("2019");

		return outlookVersionList;
	}
	
	
	public ResponseEntity<?> createLaptopDetails(LaptopEntity laptop, HttpServletRequest http) {

		SCAUtil sca = new SCAUtil();

		try {

			Long laptop_detail_id = laptop.getLaptop_details_id();

			String value = "";
			
			if (laptop.getFocusr_laptop() == null || 
					(!"N".equals(laptop.getFocusr_laptop()) && !"Y".equals(laptop.getFocusr_laptop()))) {
				return new ResponseEntity(new ApiResponse(false, "FocusR Laptop field should be Y / N: " + value),
						HttpStatus.BAD_REQUEST);
				
			}
			
			if("N".equals(laptop.getFocusr_laptop())){
				
				Long userId = sca.getUserIdFromRequest(http, tokenProvider);
				
				Optional<User> opt = userRepository.findUser(userId);

				User user = null;
				UserRoleEntity usrRole = null;

				if (!opt.isPresent()) {

					return new ResponseEntity(new ApiResponse(false, "User is not found !"),
							HttpStatus.BAD_REQUEST);
				}
				
				user = opt.get();
				
				user.setIs_laptop_details_submitted("Y");
				userRepository.save(user);
				
				return new ResponseEntity(new ApiResponse(true, "No need to fill" + value),
						HttpStatus.OK);
			}

			
			if (laptop.getMake() == null)
				value = "Make";
			if (laptop.getModel() == null)
				value = "Model";
			if (laptop.getAsset_type() == null)
				value = "Asset Type";			
			if (laptop.getLaptop_serial_no() == null)
				value = "Laptop Serial NO";
			if (laptop.getBattery_serial_no() == null)
				value = "Battery Serial NO";
			if (laptop.getOs_version() == null)
				value = "OS Version";
			if (laptop.getWindows_product_id() == null)
				value = "Window Product ID";
			if (laptop.getRam() == null)
				value = "RAM";
			if (laptop.getStorage() == null)
				value = "Storage";
			if (laptop.getAnti_virus_enabled() == null)
				value = "Anti virus enabled";
			if (laptop.getAnti_virus_type() == null)
				value = "Anti Virus Type";
			if (laptop.getEmail_configuration() == null)
				value = "Email Config";
			if (laptop.getOutlook_version() == null)
				value = "Outlook Version";
			if (laptop.getDate_of_asset_receipt() == null)
				value = "Date of Asset receipt";
			if (laptop.getCharger_working() == null)
				value = "Charger Working";
			if (laptop.getOther_software_installed() == null)
				value = "Other Software";

			if (!"".equals(value)) {

				return new ResponseEntity(new ApiResponse(false, "Should fill the mandatory field: " + value),
						HttpStatus.BAD_REQUEST);
			}

			// while update check the active status
			if (laptop_detail_id != null) {

				LaptopEntity existLaptop = laptopRepository.findLaptop(laptop_detail_id);

				if (existLaptop == null) {

					return new ResponseEntity(new ApiResponse(false, "No Laptop!"), HttpStatus.BAD_REQUEST);

				}
			}

			Long userId = sca.getUserIdFromRequest(http, tokenProvider);
			
			Optional<User> opt = userRepository.findUser(userId);

			User user = null;
			UserRoleEntity usrRole = null;

			if (!opt.isPresent()) {

				return new ResponseEntity(new ApiResponse(false, "User is not found !"),
						HttpStatus.BAD_REQUEST);
			}
			
			user = opt.get();
			
			Optional<UserRoleEntity> usrRoleEntity = userRoleRepository.findByEmpId(user.getEmployeeId());
			if (usrRoleEntity.isPresent()) {
				usrRole = usrRoleEntity.get();
			}
			
			String name = user.getName();
			String empCode = user.getEmployeeId();
			String department = usrRole.getDesignation();
			
			laptop.setEmployee_name(name);
			laptop.setEmp_code(empCode);
			laptop.setDepartment(department);
			
			laptopRepository.save(laptop);
			
			user.setIs_laptop_details_submitted("Y");
			userRepository.save(user);

		} catch (Exception e) {

			log.error("***************** Unable to save laptop details!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to save laptop details!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(laptop, HttpStatus.CREATED);
	}

	public ResponseEntity<?> getListOfLaptopDetails() {

		List<LaptopEntity> listOfLaptopDetails = null;
		SCAUtil sca = new SCAUtil();

		try {

			listOfLaptopDetails = laptopRepository.findAll();

			if (listOfLaptopDetails == null) {

				listOfLaptopDetails = new ArrayList<LaptopEntity>();
			}

		} catch (Exception e) {
			log.error("***************** Unable to get list Of Laptop Details *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable toget list Of Laptop Details!" + msg),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(listOfLaptopDetails, HttpStatus.OK);

	}

}
