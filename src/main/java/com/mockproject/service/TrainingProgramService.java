package com.mockproject.service;

<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> origin/g3_hung_branch
=======

>>>>>>> origin/g3_truong_branch
import com.mockproject.entity.TrainingProgram;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> origin/g3_hung_branch
=======
>>>>>>> origin/g3_truong_branch

@Service
@Transactional
@AllArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{
    private final TrainingProgramRepository trainingProgramRepository;
    public List<TrainingProgram> getPrograms(){
        return trainingProgramRepository.findAll();
    }


    public TrainingProgram getTrainingProgramById(long id){
        return repository.getTrainingProgramById(id);


    @Override
    public List<TrainingProgram> searchProgramP(String query) {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.searchProgramP(query);
        return trainingPrograms;
    }

    public List<TrainingProgram> getAll(Integer pageNo, Integer pageSize){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> result=repository.findAll(paging);
        return result.getContent();
    }
    public List<TrainingProgram> getByName(String keyword){
        return repository.getTrainingProgramByNameContains(keyword);
    }
    public long countAll(){
        return repository.count();
    }
    public List<TrainingProgram> getByCreatorFullname(String keyword){
        return repository.getAllByCreatorFullNameContains(keyword);

}
