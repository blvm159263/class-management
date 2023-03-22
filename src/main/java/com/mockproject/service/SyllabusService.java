package com.mockproject.service;

<<<<<<< HEAD
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.mapper.SyllabusMapper;
=======
import com.mockproject.entity.Syllabus;
>>>>>>> origin/g3_thanh_branch
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {
<<<<<<< HEAD

    private final SyllabusRepository syllabusRepository;

    @Override
    public SyllabusDTO getSyllabusById(Long id){
        Syllabus syllabus = syllabusRepository.getSyllabusById(id);
        return SyllabusMapper.INSTANCE.toDTO(syllabus);
    }

=======
    @Autowired
    private final SyllabusRepository repository;
    public Syllabus getSyllabusById(Long id){
         Optional<Syllabus> syllabusOptional = repository.findById(id);
         if(syllabusOptional.isPresent()){
             return syllabusOptional.get();
         }else {
             return new Syllabus();
         }
    }
>>>>>>> origin/g3_thanh_branch
}
