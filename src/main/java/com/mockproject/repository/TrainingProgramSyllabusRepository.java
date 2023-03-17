package com.mockproject.repository;


import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingProgramSyllabusRepository extends JpaRepository<TrainingProgramSyllabus, Long> {
    Optional<List<TrainingProgramSyllabus>> findByTrainingProgramAndStatus(TrainingProgram trainingProgram, Boolean status);

    List<TrainingProgramSyllabus> getTrainingProgramSyllabusByTrainingProgramId(long trainProgramID);

    List<TrainingProgramSyllabus> findByTrainingProgramAndStatus(TrainingProgram trainingProgram, boolean status);
}
