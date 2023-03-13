package com.mockproject.service;

import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingMaterialService implements ITrainingMaterialService {
    private final TrainingMaterialRepository repository;

}
