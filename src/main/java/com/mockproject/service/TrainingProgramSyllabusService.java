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

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramSyllabusService implements ITrainingProgramSyllabusService {

    private final TrainingProgramSyllabusRepository repository;

    @Override
    public List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(long trainProgramID) {
        return repository.getTrainingProgramSyllabusByTrainingProgramId(trainProgramID);
    }

    @Override
    public List<TrainingProgramSyllabusDTO> getAllSyllabusByTrainingProgramId(long trainProgramID,
                                                                              boolean status) {
        Optional<List<TrainingProgramSyllabus>> list = repository.findByTrainingProgramIdAndStatus(trainProgramID, status);
        ListUtils.checkList(list);

        List<TrainingProgramSyllabusDTO> trainingProgramSyllabusDTOList = new ArrayList<>();
        for (TrainingProgramSyllabus t: list.get()){
            trainingProgramSyllabusDTOList.add(TrainingProgramSyllabusMapper.INSTANCE.toDTO(t));
        }
        return trainingProgramSyllabusDTOList;
    }
}
