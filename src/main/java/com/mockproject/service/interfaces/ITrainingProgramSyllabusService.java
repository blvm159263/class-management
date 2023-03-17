package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.List;
import java.util.Optional;

import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.List;

public interface ITrainingProgramSyllabusService {

    List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(long trainProgramID);


    List<TrainingProgramSyllabusDTO> getAllSyllabusByTrainingProgramId(long trainProgramID, boolean status);

}
