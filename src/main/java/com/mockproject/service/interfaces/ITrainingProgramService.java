package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;

import java.util.List;

public interface ITrainingProgramService {

    List<TrainingProgramDTO> searchByProgramName(String name);

    List<TrainingProgramDTO> searchByCreatorName(String name);

    List<TrainingProgramDTO> getAllTrainingProgram();
}
