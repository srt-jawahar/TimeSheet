package com.foucsr.crmportal.mysql.database.repository.timesheet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.timesheet.CountryEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

	 @Query(value = "SELECT * FROM COUNTRY WHERE COUNTRY_CODE = :country_code ", nativeQuery = true)
	 CountryEntity findCountrybyCode(@Param("country_code") String country_code);

}
