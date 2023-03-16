package com.mockproject.repository;

import com.mockproject.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    List<TrainingProgram> findByNameContaining(String name);

    Optional<TrainingProgram> findFirstByNameAndStatus(String name, boolean status);

    List<TrainingProgram> findByCreatorFullNameContaining(String name);
}
