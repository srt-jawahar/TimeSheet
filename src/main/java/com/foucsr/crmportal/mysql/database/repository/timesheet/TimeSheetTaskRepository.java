package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.TimesheetTaskEntity;


@Repository
public interface TimeSheetTaskRepository extends JpaRepository<TimesheetTaskEntity, String> {
	
	@Query("SELECT u FROM TimesheetTaskEntity u WHERE u.timesheetId.id = :timesheetId")
	List<TimesheetTaskEntity> findByTimesheetId(@Param("timesheetId") String timesheetId);
	
	@Query("SELECT tst FROM TimesheetTaskEntity tst join tst.timesheetId ts WHERE tst.category = :category and ts.date BETWEEN :startDate AND :endDate and ts.Status = 'APPROVED' order by ts.date asc")
	List<TimesheetTaskEntity> findBycategory(@Param("category") String category,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Query("SELECT tst FROM TimesheetTaskEntity tst join tst.timesheetId ts WHERE ts.empId = :empId and ts.date BETWEEN :startDate AND :endDate and ts.Status in ( 'APPROVED','SUBMITTED' )  order by ts.date asc")
	List<TimesheetTaskEntity> findByEmpId(@Param("empId") String empId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	@Query("SELECT tst FROM TimesheetTaskEntity tst join tst.timesheetId ts WHERE ts.date BETWEEN :startDate AND :endDate and ts.Status in ( 'APPROVED','SUBMITTED')  and ts.empId not like 'D%' order by ts.date asc")
	List<TimesheetTaskEntity> findByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


	
}
