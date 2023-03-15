package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import org.springframework.data.domain.Page;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;

import java.time.LocalDate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ISyllabusService {

    List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId);



    Page<SyllabusDTO> getListSyllabus(boolean status,
                                      LocalDate fromDate, LocalDate toDate, String search, String[] sort, Optional<Integer> page);

    List<SyllabusDTO> getAll(boolean state, boolean status);

    List<SyllabusDTO> getSyllabusList(boolean status);

    SyllabusDTO getSyllabusById(long syllabusId,boolean state, boolean status);

    long create(SyllabusDTO syllabus, User user);

    Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException;

    boolean deleteSyllabus(long syllabusId, boolean status);

}
