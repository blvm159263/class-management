package com.mockproject.dto.mapper;


import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.function.Function;

public class TrainingProgramSyllabusDTOMapper implements Function<TrainingProgramSyllabus, TrainingProgramSyllabusDTO> {

    @Override
    public TrainingProgramSyllabusDTO apply(TrainingProgramSyllabus trainingProgramSyllabus) {
        return new TrainingProgramSyllabusDTO(
                trainingProgramSyllabus.getId(),
                trainingProgramSyllabus.isStatus(),
                trainingProgramSyllabus.getSyllabus().getId(),
                trainingProgramSyllabus.getTrainingProgram().getId()
        );
    }
}
