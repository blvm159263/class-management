package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    TrainingClass findByIdAndStatus(int id, Boolean status);

    @Query("SELECT t.trainer FROM TrainingClassUnitInformation t " +
            "WHERE t.trainingClass.id = ?1")
    List<TrainingClassUnitInformation> getAllTrainers(long id);
}
