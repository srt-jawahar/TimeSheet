package com.foucsr.crmportal.mysql.database.repository.managerapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.managerapp.KpiKraRating;

@Repository
public interface KpiKraRatingRepository extends JpaRepository<KpiKraRating, Long>{

}
