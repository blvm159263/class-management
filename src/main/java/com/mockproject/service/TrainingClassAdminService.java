package com.mockproject.service;

import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.dto.mapper.TrainingClassAdminDTOMapper;
import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.service.interfaces.ITrainingClassAdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TrainingClassAdminService implements ITrainingClassAdminService {

    private final TrainingClassAdminRepository repository;

    private final TrainingClassAdminDTOMapper mapper;
}
