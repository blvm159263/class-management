package com.mockproject.service;


import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;

    @Override
    public List<Syllabus> getAllSyllabusEntityById(List<Long> sId) {
        List<Syllabus> syllabus = syllabusRepository.getAllSyllabusByIdInAndStatus(sId,true);
        return syllabus;
    }

    @Override
    public SyllabusDTO getSyllabusById(Long id){
        Syllabus syllabus = syllabusRepository.getSyllabusById(id);
        return SyllabusMapper.INSTANCE.toDTO(syllabus);
    }

//    @Autowired
//    private final SyllabusRepository repository;
//    public Syllabus getSyllabusById(Long id){
//         Optional<Syllabus> syllabusOptional = repository.findById(id);
//         if(syllabusOptional.isPresent()){
//             return syllabusOptional.get();
//         }else {
//             return new Syllabus();
//         }
//    }

}
