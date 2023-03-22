package com.mockproject.repository;


import com.mockproject.entity.TrainingProgramSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingProgramSyllabusRepository extends JpaRepository<TrainingProgramSyllabus, Long> {

    List<TrainingProgramSyllabus> getTrainingProgramSyllabusByTrainingProgramId(Long trainProgramID);


}
