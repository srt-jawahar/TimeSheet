package com.foucsr.crmportal.mysql.database.repository.managerapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.LaptopEntity;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity, Long> {

	@Query(value = "SELECT * FROM LAPTOP_DETAILS WHERE LAPTOP_DETAILS_ID = :laptop_details_id", nativeQuery = true)
	LaptopEntity findLaptop(@Param("laptop_details_id") Long laptop_details_id);

}
