package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.UserRoleEntity;
import com.foucsr.crmportal.util.ManagerListResp;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
	
	
	Optional<UserRoleEntity> findByEmpId(String empId);

	@Query("SELECT u FROM UserRoleEntity u WHERE u.role = :role")
	List<UserRoleEntity> findByRole(@Param("role") String role);
	
	
	 @Query(value = "select UR.USER_ID, UR.DESIGNATION,ULD.name from USER_ROLE UR, \r\n" + 
		 		"USER_LOGIN_DETAILS ULD\r\n" + 
		 		" where \r\n" + 
		 		" ULD.employeeId = UR.USER_ID\r\n" + 
		 		" AND UR.Role = 'Manager'", nativeQuery = true)
		    List<ManagerListResp> getAllManagers();

}
