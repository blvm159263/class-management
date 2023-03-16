package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;

public interface ITrainingProgramService {
    TrainingProgramDTO getTraningProgramByClassId(long id);
}
