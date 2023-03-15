package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingClassUnitInformationRepository extends JpaRepository<TrainingClassUnitInformation, Long> {

    List<TrainingClassUnitInformation> findByStatusAndTrainerId(boolean status, long trainerId);
    List<TrainingClassUnitInformation> findByTrainingClassAndStatus(TrainingClass trainingClass, boolean status);
    Optional<TrainingClassUnitInformation> findByUnitAndTrainingClassAndStatus(Unit unit, TrainingClass trainingClass, boolean status);
}
