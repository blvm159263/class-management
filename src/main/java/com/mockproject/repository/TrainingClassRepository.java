package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    @Query("SELECT c FROM TrainingClass c " +
            "WHERE c.status = ?1 " +
            "AND c.location.locationName IN ?2 " +
            "AND c.startDate BETWEEN ?3 AND ?4 " +
            "AND c.startTime BETWEEN ?5 AND ?6 ")
    Page<TrainingClass> getListClass();
}
