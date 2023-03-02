package com.mockproject.dto.mapper;

import com.mockproject.dto.TrainingClassUnitInformationDTO;
import com.mockproject.entity.TrainingClassUnitInformation;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingClassUnitInformationDTOMapper implements Function<TrainingClassUnitInformation, TrainingClassUnitInformationDTO> {

    @Override
    public TrainingClassUnitInformationDTO apply(TrainingClassUnitInformation trainingClassUnitInformation) {
        return new TrainingClassUnitInformationDTO(
                trainingClassUnitInformation.getId(),
                trainingClassUnitInformation.isStatus(),
                trainingClassUnitInformation.getTrainer().getId(),
                trainingClassUnitInformation.getUnit().getId(),
                trainingClassUnitInformation.getTrainingClass().getId(),
                trainingClassUnitInformation.getTower().getId()
        );
    }
}
