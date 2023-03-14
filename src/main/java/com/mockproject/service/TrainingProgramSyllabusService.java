package com.mockproject.service;

import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgramSyllabus;
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
    public void addSyllabus(TrainingProgramSyllabus syllabus){
         repository.save(syllabus);
    }

}
