package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

public interface ITrainingProgramService {

    List<TrainingProgramDTO> searchByName(String name);

    TrainingProgram getTrainingProgramById(Long id);

    List<TrainingProgram> getAll(Integer pageNo, Integer pageSize);

    List<TrainingProgram> getByName(String keyword);

    Long countAll();

    List<TrainingProgram> getByCreatorFullname(String keyword);
    void save(Long sylId, String name);
    List<TrainingProgram> getAll();
    void downloadCsvFile(PrintWriter printWriter,List<TrainingProgram> trainingPrograms);
    List<TrainingProgram> GetTrainingProgramDataFromCsv(InputStream fileInputStream, Long userId) throws IOException;
    void allowCsvFile(MultipartFile file, Long userId, String check)throws IOException;
    void replaceCsvFile(MultipartFile file, Long userId, String check)throws IOException;
    void skipCsvFile(MultipartFile file, Long userId, String check)throws IOException;
}
