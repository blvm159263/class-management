package com.mockproject.service;

import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TrainingProgramSyllabusService implements ITrainingProgramSyllabusService {
    private final TrainingProgramSyllabusRepository repository;

}
