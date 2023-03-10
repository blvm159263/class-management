package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

//    @Query("SELECT c FROM TrainingClass c " +
//            "WHERE c.status = ?1 " +
//            "AND c.")
//    Page<TrainingClass> getListClass();

    List<TrainingClass> findByClassCode(String code);
}
