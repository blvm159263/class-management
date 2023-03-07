package com.mockproject.repository;

import com.mockproject.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    List<ClassSchedule> findAllByDateBetweenAndTrainingClassId(LocalDate StartDate, LocalDate endDate, Long id);

    Long countAllByDateBetweenAndTrainingClassId(LocalDate StartDate,LocalDate endDate,Long id);
    Long countAllByDateBeforeAndTrainingClassId(LocalDate date,Long id);


}
