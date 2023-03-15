package com.mockproject.repository;

import com.mockproject.entity.TrainingClassUnitInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingClassUnitInformationRepository extends JpaRepository<TrainingClassUnitInformation, Long> {
    public List<TrainingClassUnitInformation> findAllByTrainingClassId(Long Id);


    List<TrainingClassUnitInformation> findByStatusAndTrainerId(boolean status, long trainerId);
}
