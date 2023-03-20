package com.mockproject.service;

import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.mapper.TrainingProgramSyllabusMapper;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramSyllabusService implements ITrainingProgramSyllabusService {

    private final TrainingProgramSyllabusRepository repository;

}
