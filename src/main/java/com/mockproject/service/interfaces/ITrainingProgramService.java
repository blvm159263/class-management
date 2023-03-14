package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;

import java.util.List;

public interface ITrainingProgramService {

    List<TrainingProgramDTO> searchByName(String name);

    
}
