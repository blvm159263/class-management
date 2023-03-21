package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISyllabusService {

    List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId);

    List<SyllabusDTO> readFileCsv(MultipartFile file, User user) throws IOException;

    byte[] getTemplateCsvFile() throws IOException;
}
