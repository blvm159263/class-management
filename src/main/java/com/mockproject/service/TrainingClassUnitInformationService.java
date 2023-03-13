package com.mockproject.service;

import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.ITrainingClassUnitInformationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TrainingClassUnitInformationService implements ITrainingClassUnitInformationService {
    private final TrainingClassUnitInformationRepository repository;

    @Override
    public List<TrainingClassUnitInformation> findAllByTrainingClassId(Long id) {
        return repository.findAllByTrainingClassId(id);
    }
}
