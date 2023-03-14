package com.mockproject.repository;

<<<<<<< HEAD
<<<<<<< HEAD

=======
import com.mockproject.dto.TrainingProgramDTO;
>>>>>>> origin/g3_hung_branch
=======

>>>>>>> origin/g3_truong_branch
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    TrainingProgram getTrainingProgramById(long id);

    List<TrainingProgram> getTrainingProgramByNameContains(String name);

    List<TrainingProgram> getAllByCreatorFullNameContains(String name);



}
