package com.mockproject.service;

<<<<<<< HEAD
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.mapper.TrainingProgramMapper;
=======
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
>>>>>>> origin/g3_thanh_branch
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
=======
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
>>>>>>> origin/g3_thanh_branch
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
<<<<<<< HEAD

=======
    @Autowired
>>>>>>> origin/g3_thanh_branch
    private final TrainingProgramRepository repository;
    public void save(TrainingProgram trainingProgram){
        repository.save(trainingProgram);
    }
    public List<TrainingProgram> getAll(){
        return repository.findAll();
    }

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
