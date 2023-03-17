package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISyllabusService {

    List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId);



    Page<SyllabusDTO> getListSyllabus(boolean status,
                                      LocalDate fromDate, LocalDate toDate,
                                      List<String> search, String[] sort, Optional<Integer> page);

    List<Long> getListSyllabusIdByOSD(String osd);

    List<SyllabusDTO> getAll(boolean state, boolean status);

    List<SyllabusDTO> getSyllabusList(boolean status);

    SyllabusDTO getSyllabusById(Long syllabusId,boolean state, boolean status);

    Long create(SyllabusDTO syllabus, User user);

    Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException;

    boolean deleteSyllabus(Long syllabusId, boolean status);

    List<Long> getListSyllabusIdByOSD(String osd);

    Syllabus getSyllabusById(Long id);
}
