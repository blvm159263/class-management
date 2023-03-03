package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.mapper.SyllabusDTOMapper;
import com.mockproject.entity.Syllabus;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;

    private final SyllabusDTOMapper mapper;

    public List<SyllabusDTO> getAll(){
        List<Syllabus> a = syllabusRepository.findAll();

        return mapper.toDTOs(a);
    }
}
