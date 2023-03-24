package com.mockproject.repository;

import com.mockproject.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    TrainingProgram getTrainingProgramById(Long id);

    List<TrainingProgram> getTrainingProgramByNameContains(String name);

    List<TrainingProgram> getAllByCreatorFullNameContains(String name);

    List<TrainingProgram> findByNameContainingAndStatus(String name, boolean status);

    Optional<TrainingProgram> findFirstByNameAndStatus(String name, boolean status);
    boolean existsByName(String name);
    boolean existsByProgramId(int id);
    List<TrainingProgram> getTrainingProgramByProgramId(int id);
    List<TrainingProgram> getTrainingProgramByName(String name);
    List<TrainingProgram> getTrainingProgramByNameAndProgramId(String name,int id);
}
