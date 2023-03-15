package com.mockproject.service;


import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramSyllabusService implements ITrainingProgramSyllabusService {
    private final TrainingProgramSyllabusRepository repository;


    public List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(long trainProgramID){
        return repository.getTrainingProgramSyllabusByTrainingProgramId(trainProgramID);
    }
}
