package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.dto.mapper.TrainingClassDTOMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;

    private final TrainingClassDTOMapper mapper;
}
