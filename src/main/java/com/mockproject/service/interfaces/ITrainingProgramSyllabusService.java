package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgramSyllabus;

import java.util.List;
import java.util.Optional;

public interface ITrainingProgramSyllabusService {

    List<TrainingProgramSyllabusDTO> getAllSyllabusByTrainingProgramId(long trainProgramID, boolean status);

}
