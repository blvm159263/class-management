package com.mockproject.service;

import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    @Autowired
    private final TrainingProgramRepository repository;
    public void save(TrainingProgram trainingProgram){
        repository.save(trainingProgram);
    }
    public List<TrainingProgram> getAll(){
        return repository.findAll();
    }

}
