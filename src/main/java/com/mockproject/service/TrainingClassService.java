package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;


    @Override
    public TrainingClassDTO create(TrainingClassDTO trainingClassDTO) {
        try{
            TrainingClass trainingClass = repository.save(TrainingClassMapper.INSTANCE.toEntity(trainingClassDTO));
            return TrainingClassMapper.INSTANCE.toDTO(trainingClass);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
//        return train;
    }
}
