package com.mockproject.dto.mapper;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingProgramDTOMapper implements Function<TrainingProgram, TrainingProgramDTO> {

    @Override
    public TrainingProgramDTO apply(TrainingProgram trainingProgram) {
        return new TrainingProgramDTO(
                trainingProgram.getId(),
                trainingProgram.getDateCreated(),
                trainingProgram.getLastDateModified(),
                trainingProgram.getHour(),
                trainingProgram.getDay(),
                trainingProgram.isStatus(),
                trainingProgram.getCreator().getId(),
                trainingProgram.getLastModifier().getId()
        );
    }
}
