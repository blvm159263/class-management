package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long>, JpaSpecificationExecutor<TrainingClass> {

}
