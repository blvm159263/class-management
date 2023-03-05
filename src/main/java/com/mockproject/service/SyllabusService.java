package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.mapper.SyllabusDTOMapper;
import com.mockproject.entity.Syllabus;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository repository;

    private final SyllabusDTOMapper mapper;

    @Override
    public SyllabusDTO get(long id) {
        return null;
//        return repository
//                .findById(id)
//                ;
    }

    @Override
    public List<SyllabusDTO> getAll() {
        List<Syllabus> syllabusList = repository.findAll();
        if(syllabusList.isEmpty())
            throw new RuntimeException("No syllabus exist");
        List<SyllabusDTO> list = new ArrayList<>();
        syllabusList.forEach(i -> list.add(mapper.apply(i)));
        return list;
    }
}
