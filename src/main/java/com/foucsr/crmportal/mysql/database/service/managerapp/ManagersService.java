package com.foucsr.crmportal.mysql.database.service.managerapp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foucsr.crmportal.mysql.database.repository.timesheet.UserRoleRepository;
import com.foucsr.crmportal.payload.ApiResponse;
import com.foucsr.crmportal.util.ManagerListResp;
import com.foucsr.crmportal.util.SCAUtil;

@Service
public class ManagersService {

	Logger log = LoggerFactory.getLogger(ManagersService.class);

	@Autowired
	private UserRoleRepository managerRepository;

	public ResponseEntity<?> getManagers() {

		List<ManagerListResp> managers = null;

		try {

			managers = managerRepository.getAllManagers();

			if (managers == null) {

				managers = new ArrayList<ManagerListResp>();
			}

		} catch (Exception e) {

			SCAUtil sca = new SCAUtil();

			log.error("***************** Unable to get Managers!  *********************\n" + e);

			String msg = sca.getErrorMessage(e);

			return new ResponseEntity(new ApiResponse(false, "Unable to get Managers!" + msg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(managers, HttpStatus.OK);

	}
}
