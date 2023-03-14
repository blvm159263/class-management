package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface ITrainingProgramService {
    List<TrainingProgram> searchProgramP(String query);

}
