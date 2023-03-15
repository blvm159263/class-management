package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;

import com.mockproject.entity.User;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SyllabusService implements ISyllabusService{

    private final SyllabusRepository syllabusRepository;
    private final SessionService sessionService;

    // List syllabus for user
    @Override
    public List<SyllabusDTO> getAll(boolean state, boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findByStateAndStatus(state, status);
        ListUtils.checkList(syllabusList);

        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();
        for (Syllabus s : syllabusList.get()){
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    // List syllabus for admin
    @Override
    public List<SyllabusDTO> getSyllabusList(boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findAllByStatus(status);
        ListUtils.checkList(syllabusList);
        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();

        for (Syllabus s: syllabusList.get()) {
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    @Override
    public SyllabusDTO getSyllabusById(long syllabusId,boolean state, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, state, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        SyllabusDTO syllabusDTO = SyllabusMapper.INSTANCE.toDTO(syllabus.get());
        List<SessionDTO> sessionDTOList = sessionService.getAllSessionBySyllabusId(syllabusId, true);
        syllabusDTO.setSessionDTOList(sessionDTOList);
        return syllabusDTO;
    }

    @Override
    public long create(SyllabusDTO syllabus, User user){
        syllabus.setCreatorId(user.getId());
        syllabus.setLastModifierId(user.getId());
        syllabus.setDateCreated(java.time.LocalDate.now());
        syllabus.setLastDateModified(java.time.LocalDate.now());
        syllabus.setHour(BigDecimal.valueOf(0));
        Syllabus newSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabus));
        sessionService.createSession(newSyllabus.getId(), syllabus.getSessionDTOList(), user);
        return newSyllabus.getId();
    }

    @Override
    public Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException{
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusDTO.getId(), status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Set day and hour
        syllabus.get().setDay(0);
        syllabus.get().setHour(BigDecimal.valueOf(0));
        syllabusRepository.save(syllabus.get());

        syllabusDTO.setLastModifierId(user.getUser().getId());
        syllabusDTO.setLastDateModified(java.time.LocalDate.now());

        if(syllabusDTO.isStatus() == true)
        {
            for (SessionDTO s : syllabusDTO.getSessionDTOList()) {
                if (s.getId() == null) {
                    sessionService.createSession(syllabusDTO.getId(), s, user.getUser());
                }else {
                    sessionService.editSession(s, true);
                }
            }
        } else {
            sessionService.deleteSessions(syllabusDTO.getId(), status);
        }
        syllabus = syllabusRepository.findByIdAndStatus(syllabusDTO.getId(), true);
        syllabusDTO.setHour(syllabus.get().getHour());
        syllabusDTO.setDay(syllabus.get().getListSessions().size());

        return syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabusDTO));
    }

    public boolean deleteSyllabus(long syllabusId, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusId, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setStatus(false);
        sessionService.deleteSessions(syllabusId, status);
        syllabusRepository.save(syllabus.get());
        return true;
    }
}
