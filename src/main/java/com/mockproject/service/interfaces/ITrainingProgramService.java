package com.mockproject.service.interfaces;

import com.mockproject.entity.TrainingProgram;

import java.util.List;

public interface ITrainingProgramService {
    TrainingProgram getTrainingProgramById(Long id);

    List<TrainingProgram> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Long countAll();

    List<TrainingProgram> getByCreatorFullname(String keyword);
}
