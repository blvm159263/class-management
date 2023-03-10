package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    private final TrainingProgramRepository trainingProgramRepository;

    private TrainingProgramDTO convertEntityToDto(TrainingProgram trainingProgram){
        TrainingProgramDTO trainingProgramDTO = new TrainingProgramDTO();
        trainingProgramDTO.setId(trainingProgram.getId());
        trainingProgramDTO.setName(trainingProgram.getName());
        trainingProgramDTO.
    }

    public List<TrainingProgram> getAll(Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> result=trainingProgramRepository.findAll(paging);
        return result.getContent();
    }
    public List<TrainingProgram> getByName(String keyword){
        return trainingProgramRepository.getTrainingProgramByNameContains(keyword);
    }
    public long countAll(){
        return trainingProgramRepository.count();
    }
    public List<TrainingProgram> getByCreatorFullname(String keyword){
        return trainingProgramRepository.getAllByCreatorFullNameContains(keyword);
    }
}
