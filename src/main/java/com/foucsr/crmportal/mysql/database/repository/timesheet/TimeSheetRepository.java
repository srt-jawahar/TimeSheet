package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.User;
import com.foucsr.crmportal.mysql.database.model.timesheet.TimeSheetEntity;




@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, String> {

	@Query("SELECT u FROM TimeSheetEntity u WHERE u.empId = :userid and u.date = :date")
	Optional<TimeSheetEntity> findByIDandDate(@Param("userid") String userid, @Param("date") Date date);
	
	@Query("SELECT u FROM TimeSheetEntity u WHERE u.date BETWEEN :startDate AND :endDate and u.empId not like 'D%' order by u.date asc")
	List<TimeSheetEntity> getMonthData(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	

	@Query("SELECT u FROM TimeSheetEntity u WHERE  u.empId = :userid and u.date BETWEEN :startDate AND :endDate order by u.date asc")
	List<TimeSheetEntity> getMonthDataByEmp(@Param("userid") String userid,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	
	@Query(value = "SELECT * FROM TIMESHEET WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY TIMESHEET_ID DESC", nativeQuery = true)
	List<TimeSheetEntity> getPendingForApproval(@Param("manager_id") String manager_id);
	
	@Query(value = "SELECT * FROM TIMESHEET WHERE emp_Id = :id  AND date = :tsDate ", nativeQuery = true)
	Optional<TimeSheetEntity> findByIdandDate(String id, Date tsDate);
	
	@Query(value = "SELECT count(date) FROM TIMESHEET WHERE emp_Id = :userId  AND date = :tsDate ", nativeQuery = true)
	Double findCountByIDandDate(String userId, Date tsDate);
	
	

	

}
