package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;

public interface ITrainingProgramService {
    TrainingProgramDTO getTraningProgramByClassCode(String code, boolean status);
}
