package com.mockproject.repository;

<<<<<<< HEAD
import com.mockproject.dto.TrainingProgramDTO;
=======
import com.mockproject.entity.Syllabus;
>>>>>>> origin/g3_thanh_branch
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

<<<<<<< HEAD
import java.util.Optional;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    Page<TrainingProgram> findAllByNameContainingOrCreatorFullNameContaining(Pageable pageable, String name, String name2);

    Optional<TrainingProgram> findFirstByNameAndStatus(String name, boolean status);

    TrainingProgram getTrainingProgramById(Long id);
=======
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

>>>>>>> origin/g3_thanh_branch
}
