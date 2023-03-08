package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    private final TrainingProgramRepository trainingProgramRepository;
    public List<TrainingProgram> getPrograms(){
        return trainingProgramRepository.findAll();
    }


    @Override
    public List<TrainingProgram> searchProgramP(String query) {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.searchProgramP(query);
        return trainingPrograms;
    }

}
