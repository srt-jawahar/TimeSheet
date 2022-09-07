package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.OverTime;

@Repository
public interface OverTimeRepository extends JpaRepository<OverTime, String>{
	

	@Query("SELECT u FROM OverTime u WHERE u.empId = :userid and u.date = :date")
	Optional<OverTime> findByIDandDate(@Param("userid") String userid, @Param("date") Date date);
	
	@Query("SELECT u FROM OverTime u WHERE u.date BETWEEN :startDate AND :endDate and u.empId not like 'D%' order by u.date asc")
	List<OverTime> getMonthData(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	

	@Query("SELECT u FROM OverTime u WHERE  u.empId = :userid and u.date BETWEEN :startDate AND :endDate order by u.date asc")
	List<OverTime> getMonthDataByEmp(@Param("userid") String userid,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	
	@Query(value = "SELECT * FROM OVER_TIME WHERE APPROVER = :manager_id AND STATUS = 'SUBMITTED' ORDER BY OVER_TIME_ID DESC", nativeQuery = true)
	List<OverTime> getPendingForApproval(@Param("manager_id") String manager_id);
	
}
