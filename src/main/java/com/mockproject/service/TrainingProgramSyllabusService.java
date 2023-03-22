package com.mockproject.service;


import com.mockproject.dto.TrainingProgramSyllabusDTO;

import com.mockproject.entity.TrainingProgramSyllabus;

import com.mockproject.mapper.TrainingProgramSyllabusMapper;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramSyllabusService implements ITrainingProgramSyllabusService {


    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    @Override
    public List<TrainingProgramSyllabusDTO> getTrainingProgramSyllabusListById(Long trainingProgramID) {
        return trainingProgramSyllabusRepository.getTrainingProgramSyllabusByTrainingProgramId(trainingProgramID).stream().map(TrainingProgramSyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

//    private final TrainingProgramSyllabusRepository repository;
//
//    @Override
//    public List<TrainingProgramSyllabusDTO> getTrainingProgramSyllabusListById(Long trainProgramID) {
//        return repository.getTrainingProgramSyllabusByTrainingProgramId(trainProgramID).stream().map(TrainingProgramSyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
//    }
//
    @Override
    public List<TrainingProgramSyllabusDTO> getAllSyllabusByTrainingProgramId(long trainProgramID,
                                                                              boolean status) {
        Optional<List<TrainingProgramSyllabus>> list = trainingProgramSyllabusRepository.findByTrainingProgramIdAndStatus(trainProgramID, status);
        ListUtils.checkList(list);

        List<TrainingProgramSyllabusDTO> trainingProgramSyllabusDTOList = new ArrayList<>();
        for (TrainingProgramSyllabus t: list.get()){
            trainingProgramSyllabusDTOList.add(TrainingProgramSyllabusMapper.INSTANCE.toDTO(t));
        }
        return trainingProgramSyllabusDTOList;
    }
//
//    public void addSyllabus(TrainingProgramSyllabus syllabus){
//        repository.save(syllabus);
//
//    }
}
