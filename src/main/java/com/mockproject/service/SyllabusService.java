package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;

import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final SessionService sessionService;

    public List<Syllabus> getAll(){
        return syllabusRepository.findByStateAndStatus(true, true);
    }

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
}
