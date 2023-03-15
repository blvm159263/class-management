package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface ITrainingProgramService {
    TrainingProgram getTrainingProgramById(Long id);

    List<TrainingProgram> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Long countAll();

    List<TrainingProgram> getByCreatorFullname(String keyword);
}
