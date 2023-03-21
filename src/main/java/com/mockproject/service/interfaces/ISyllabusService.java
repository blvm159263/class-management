package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISyllabusService {

    List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId);

    List<Long> getListSyllabusIdByOSD(String osd);

    List<SyllabusDTO> getAll(boolean state, boolean status);

    List<SyllabusDTO> getSyllabusList(boolean status);

    SyllabusDTO getSyllabusById(Long syllabusId,boolean state, boolean status);

    boolean replace(SyllabusDTO syllabusDTO, boolean status);

    Long create(SyllabusDTO syllabus, User user);

    Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException;

    boolean deleteSyllabus(Long syllabusId, boolean status);

    Syllabus getSyllabusById(Long id);

    SyllabusDTO readFileCsv(MultipartFile file, int condition, int handle) throws IOException;

    byte[] getTemplateCsvFile() throws IOException;
}
