package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;


    @Override
    public TrainingClassDTO getTrainingClassByClassCode(String code) {
        TrainingClass trainingClass = repository.findByClassCodeAndStatus(code, true).get(0);
        if(trainingClass.isStatus()) {
            return TrainingClassMapper.INSTANCE.toDTO(trainingClass);
        }
        return null;
    }
}