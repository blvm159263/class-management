package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITrainingProgramService {
    List<TrainingProgramDTO> searchByName(String name);

    TrainingProgramDTO getTrainingProgramById(Long id);

    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2);

    Long countAll();

    void save(Long sylId, String name);

    List<TrainingProgramDTO> getByCreatorFullname(String keyword);
}
