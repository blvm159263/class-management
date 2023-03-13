package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;

import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.utils.ListUtils;
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
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final SessionService sessionService;

    // List syllabus for user
    public List<Syllabus> getAll(boolean state, boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findByStateAndStatus(state, status);
        ListUtils.checkList(syllabusList);
        return syllabusList.get();
    }

    // List syllabus for admin
    public List<Syllabus> getSyllabusList(boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findAllByStatus(status);
        ListUtils.checkList(syllabusList);
        return syllabusList.get();
    }

    public Syllabus getSyllabusById(long syllabusId,boolean state, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, state, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        return syllabus.get();
    }

    public long create(SyllabusDTO syllabus, long userId){
        syllabus.setCreatorId(userId);
        syllabus.setLastModifierId(userId);
        Syllabus newSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabus));
        sessionService.createSession(newSyllabus.getId(), syllabus.getSessionDTOList(), syllabus.getCreatorId());
        return newSyllabus.getId();
    }

    public Syllabus editSyllabus(long id, SyllabusDTO syllabusDTO, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(id, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabusDTO.setId(id);
        Syllabus updateSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabusDTO));
        return updateSyllabus;
    }

    public boolean deleteSyllabus(long syllabusId, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusId, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setStatus(false);
        System.out.println("Syllabus: "+syllabusId);
        sessionService.deleteSessions(syllabusId, status);
        syllabusRepository.save(syllabus.get());
        return true;
    }
}
