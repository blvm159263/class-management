package com.mockproject.repository;

<<<<<<< HEAD
=======
import com.mockproject.entity.Syllabus;
>>>>>>> g3_branch
import com.mockproject.entity.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    Page<TrainingProgram> findAllByNameContainingOrCreatorFullNameContaining(Pageable pageable, String name, String name2);

    Optional<TrainingProgram> findFirstByNameAndStatus(String name, boolean status);

    TrainingProgram getTrainingProgramById(Long id);
<<<<<<< HEAD

    List<TrainingProgram> getTrainingProgramByNameContains(String name);

    List<TrainingProgram> getAllByCreatorFullNameContains(String name);

    List<TrainingProgram> findByNameContainingAndStatus(String name, boolean status);

=======
    boolean existsByProgramIdOrName(int id,String name);
    boolean existsByProgramId(int id);
    boolean existsByName(String name);
    TrainingProgram findTopByOrderByIdDesc();

>>>>>>> g3_branch
}
