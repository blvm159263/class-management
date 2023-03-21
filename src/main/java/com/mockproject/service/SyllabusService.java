package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;

    @Override
    public SyllabusDTO getSyllabusById(Long id){
        Syllabus syllabus = syllabusRepository.getSyllabusById(id);
        return SyllabusMapper.INSTANCE.toDTO(syllabus);
    }

}
