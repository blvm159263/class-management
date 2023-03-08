package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;

import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final SessionService sessionService;

    @Override
    public List<Syllabus> getAll(){
        return syllabusRepository.findByStateAndStatus(true, true);
    }

    @Override
    public Syllabus getSyllabus(long id){
        Syllabus syllabus = syllabusRepository.findByIdAndStateAndStatus(id, true, true);
        if (syllabus != null){
            List<Session> listSession = sessionService.getAllSessionBySyllabusId(id,true);
            syllabus.setListSessions(listSession);
            return syllabus;
        } else throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public long create(SyllabusDTO syllabus){
        Syllabus newSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabus));
        return newSyllabus.getId();
    }

    public Syllabus editSyllabus(long id, SyllabusDTO syllabusDTO){
        Optional<Syllabus> syllabus = Optional.ofNullable(syllabusRepository.findByIdAndStatus(id, true));
        syllabus.orElseThrow(() -> new RuntimeException("Syllabus doesn't exist or has been deleted."));
        syllabusDTO.setId(id);
        Syllabus updateSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabusDTO));
        return updateSyllabus;
    }
}
