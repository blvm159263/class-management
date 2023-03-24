package com.mockproject.service.interfaces;

import com.mockproject.dto.ReadFileDto;
import com.mockproject.dto.TrainingProgramAddDto;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface ITrainingProgramService {
    List<TrainingProgramDTO> searchByName(String name);

    TrainingProgramDTO getTrainingProgramById(Long id);

    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2);

    Long countAll();
<<<<<<< HEAD

    void save(Long sylId, String name);

    List<TrainingProgramDTO> getByCreatorFullname(String keyword);
=======
    Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize);
    TrainingProgramDTO getTrainingProgramById(Long id);
    void save(TrainingProgramAddDto trainingProgramDTO, HashMap<TrainingProgram, List<Long>> trainingProgramHashMap, ReadFileDto readFileDto);
    String addFromFileCsv( MultipartFile file, ReadFileDto readFileDto);
>>>>>>> g3_branch
}
