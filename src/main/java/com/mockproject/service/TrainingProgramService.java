package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    private final TrainingProgramRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public TrainingProgramDTO getTraningProgramByClassCode(String code) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        if(trainingClass.getTrainingProgram().isStatus()) {
            return TrainingProgramMapper.INSTANCE.toDTO(trainingClass.getTrainingProgram());
        }
        return null;
    }

}
