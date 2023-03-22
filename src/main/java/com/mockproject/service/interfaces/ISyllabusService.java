package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;

import java.util.List;

public interface ISyllabusService {
    List<Syllabus> getAllSyllabusEntityById(List<Long> id);
    SyllabusDTO getSyllabusById(Long id);
}
