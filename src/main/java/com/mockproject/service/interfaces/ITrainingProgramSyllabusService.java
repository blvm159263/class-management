package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.List;

public interface ITrainingProgramSyllabusService {

    List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(Long trainProgramID);


    List<TrainingProgramSyllabusDTO> getAllSyllabusByTrainingProgramId(long trainProgramID, boolean status);

}
