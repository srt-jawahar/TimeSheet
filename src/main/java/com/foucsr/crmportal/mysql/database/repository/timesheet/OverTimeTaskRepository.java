package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.OverTimeTask;

@Repository
public interface OverTimeTaskRepository extends JpaRepository<OverTimeTask, String> {

	@Query(value = "SELECT * FROM OVERTIME_TASK WHERE OVER_TIME_ID = :overtimeId "
			+ " ORDER BY OT_TASK_ID", nativeQuery = true)
	List<OverTimeTask> findByOverTimeId(@Param("overtimeId") String overtimeId);

	@Query("SELECT tst FROM OverTimeTask tst join tst.overtimeId ts WHERE tst.category = :category and ts.date BETWEEN :startDate AND :endDate and ts.Status = 'APPROVED' order by ts.date asc")
	List<OverTimeTask> findBycategory(@Param("category") String category, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT tst FROM OverTimeTask tst join tst.overtimeId ts WHERE ts.empId = :empId and ts.date BETWEEN :startDate AND :endDate and ts.Status in ( 'APPROVED','SUBMITTED' )  order by ts.date asc")
	List<OverTimeTask> findByEmpId(@Param("empId") String empId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT tst FROM OverTimeTask tst join tst.overtimeId ts WHERE ts.date BETWEEN :startDate AND :endDate and ts.Status in ( 'APPROVED','SUBMITTED')  and ts.empId not like 'D%' order by ts.date asc")
	List<OverTimeTask> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
