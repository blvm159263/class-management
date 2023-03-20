package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface ITrainingProgramService {

    Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2);
    Page<TrainingProgramDTO> findByCreatorFullNameContaining(Integer pageNo, Integer pageSize, String name);
    long countAll();
    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);
}
