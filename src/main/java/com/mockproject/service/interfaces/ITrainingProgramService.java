package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;

import java.util.List;

public interface ITrainingProgramService {

    List<TrainingProgramDTO> searchByName(String name);

    TrainingProgram getTrainingProgramById(Long id);

    List<TrainingProgram> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Long countAll();

    List<TrainingProgram> getByCreatorFullname(String keyword);
}
