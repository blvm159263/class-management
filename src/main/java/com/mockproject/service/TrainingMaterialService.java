package com.mockproject.service;

import com.mockproject.entity.TrainingMaterial;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingMaterialService implements ITrainingMaterialService {
    private final TrainingMaterialRepository repository;
    public List<TrainingMaterial> getListTrainingMaterialByUnitDetailId(long id){
        return repository.getListTrainingMaterialByUnitDetailId(id);
    }

}
