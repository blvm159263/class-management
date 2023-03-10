package com.mockproject.service;

import com.mockproject.dto.*;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.*;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;


    @Override
    public TrainingClassDTO getTrainingClassByClassCode(String code, boolean status) {
        TrainingClass trainingClass = repository.findByClassCodeAndStatus(code, status).get(0);
        return TrainingClassMapper.INSTANCE.toDTO(trainingClass);
    }
}