package com.mockproject.dto.mapper;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingMaterialDTOMapper implements Function<TrainingMaterial, TrainingMaterialDTO> {

    @Override
    public TrainingMaterialDTO apply(TrainingMaterial trainingMaterial) {
        return new TrainingMaterialDTO(
                trainingMaterial.getId(),
                trainingMaterial.getUploadDate(),
                trainingMaterial.getData(),
                trainingMaterial.getName(),
                trainingMaterial.getType(),
                trainingMaterial.getSize(),
                trainingMaterial.isStatus(),
                trainingMaterial.getUnitDetail().getId(),
                trainingMaterial.getUser().getId()
        );
    }
}
