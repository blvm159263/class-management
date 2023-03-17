package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ITrainingProgramService {

    Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2);
    Page<TrainingProgramDTO> findByCreatorFullNameContaining(Integer pageNo, Integer pageSize, String name);
    long countAll();
    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);
}
