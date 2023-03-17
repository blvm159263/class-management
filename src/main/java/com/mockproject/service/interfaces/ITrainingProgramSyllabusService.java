package com.mockproject.service.interfaces;

import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.List;

public interface ITrainingProgramSyllabusService {

    List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(long trainProgramID);

}
