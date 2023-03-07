package com.mockproject.service;

import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.ITrainingClassUnitInformationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassUnitInformationService implements ITrainingClassUnitInformationService {
    private final TrainingClassUnitInformationRepository repository;

}
