package com.mockproject.repository;

import com.mockproject.entity.TrainingClassUnitInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingClassUnitInformationRepository extends JpaRepository<TrainingClassUnitInformation, Long> {
}
