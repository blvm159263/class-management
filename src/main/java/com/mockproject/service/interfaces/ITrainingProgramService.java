package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

public interface ITrainingProgramService {
    List<TrainingProgramDTO> searchByName(String name);

    TrainingProgram getTrainingProgramById(Long id);

    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2);

    Long countAll();

    void save(Long sylId, String name);

    List<TrainingProgram> getByCreatorFullname(String keyword);
}
