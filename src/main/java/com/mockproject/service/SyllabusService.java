package com.mockproject.service;

import com.mockproject.entity.Syllabus;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {
    @Autowired
    private final SyllabusRepository repository;
    public List<Syllabus> getSyllabusName(String name){
        return repository.getSyllabusByNameContains(name);
    }
    public Syllabus getSyllabusById(Long id){
         Optional<Syllabus> syllabusOptional = repository.findById(id);
         if(syllabusOptional.isPresent()){
             return syllabusOptional.get();
         }else {
             return new Syllabus();
         }
    }
}
