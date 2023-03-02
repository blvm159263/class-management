package com.mockproject.service;

import com.mockproject.dto.mapper.TrainingProgramDTOMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    private final TrainingProgramRepository repository;

    private final TrainingProgramDTOMapper mapper;
}