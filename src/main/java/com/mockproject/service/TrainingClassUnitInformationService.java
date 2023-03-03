package com.mockproject.service;

import com.mockproject.dto.mapper.TrainingClassUnitInformationDTOMapper;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.ITrainingClassUnitInformationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TrainingClassUnitInformationService implements ITrainingClassUnitInformationService {
    private final TrainingClassUnitInformationRepository repository;

    private final TrainingClassUnitInformationDTOMapper mapper;
}
