package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);

}
