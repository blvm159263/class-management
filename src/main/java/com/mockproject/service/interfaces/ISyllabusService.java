package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;

public interface ISyllabusService {

    List<SyllabusDTO> getAll(boolean state, boolean status);
    List<SyllabusDTO> getSyllabusList(boolean status);
    SyllabusDTO getSyllabusById(long syllabusId,boolean state, boolean status);
    long create(SyllabusDTO syllabus, User user);
    Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException;
    boolean deleteSyllabus(long syllabusId, boolean status);

}
