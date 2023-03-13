package com.mockproject.service;

import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;

}
