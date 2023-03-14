package com.mockproject.service;

import com.mockproject.entity.Syllabus;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository repository;

    public Syllabus getSyllabusById(long id){
        return repository.getSyllabusById(id);
    }

}
