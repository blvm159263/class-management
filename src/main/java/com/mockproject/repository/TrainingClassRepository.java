package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    List<TrainingClass> findByClassNameContaining(String name);
}
