package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{

    private final TrainingProgramRepository repository;

    @Override
    public List<TrainingProgramDTO> searchByProgramName(String name) {
        return repository.findByNameContaining(name).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TrainingProgramDTO> searchByCreatorName(String name) {
        return repository.findByCreatorFullNameContaining(name).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TrainingProgramDTO> getAllTrainingProgram() {
        return repository.findAll().stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
