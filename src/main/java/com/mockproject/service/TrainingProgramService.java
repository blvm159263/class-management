package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{

    private final TrainingProgramRepository repository;

    @Override
    public Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> page = repository.findAllByNameContainingOrCreatorFullNameContaining(pageable, name,name2);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }

    public Long countAll() {
        return repository.count();
    }

    @Override
    public Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<TrainingProgram> page = repository.findAll(pageable);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }
    @Override
    public TrainingProgramDTO getTrainingProgramById(Long id) {
        TrainingProgram trainingProgram = repository.getTrainingProgramById(id);
        return TrainingProgramMapper.INSTANCE.toDTO(trainingProgram);
    }

}
