package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.service.interfaces.ISessionService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;
    private final UnitService unitService;
    private final SyllabusRepository syllabusRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, UnitService unitService, SyllabusRepository syllabusRepository) {
        this.sessionRepository = sessionRepository;
        this.unitService = unitService;
        this.syllabusRepository = syllabusRepository;
    }

    public List<Session> getAllSessionBySyllabusId(long syllabusId, boolean status) {
        Optional<List<Session>> listSession = sessionRepository.findBySyllabusIdAndStatus(syllabusId, status);
        ListUtils.checkList(listSession);
        return listSession.get();
    }

    public boolean createSession(long syllabusId, List<SessionDTO> listSession, long userId){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, true,true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        listSession.forEach((i) ->
        {
            i.setSyllabusId(syllabusId);
            Session session = sessionRepository.save(SessionMapper.INSTANCE.toEntity(i));
            System.out.println("Session :"+session.getId());
            unitService.createUnit(session.getId(), i.getUnitDTOList(), userId);
        });
        return true;
    }

    public Session editSession(long id, SessionDTO sessionDTO, boolean status){
        Optional<Session> session = sessionRepository.findByIdAndStatus(id, status);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        sessionDTO.setId(id);
        sessionDTO.setSyllabusId(session.get().getSyllabus().getId());
        Session updateSession = sessionRepository.save(SessionMapper.INSTANCE.toEntity(sessionDTO));
        return updateSession;
    }

    public boolean deleteSession(long sessionId, boolean status){
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionId, status);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        session.get().setStatus(false);
        System.out.println("Sessioln: "+sessionId);
        unitService.deleteUnits(sessionId, status);
        sessionRepository.save(session.get());
        return true;
    }

    public boolean deleteSessions(long syllabusId, boolean status){
        Optional<List<Session>> sessions = sessionRepository.findBySyllabusIdAndStatus(syllabusId, status);
        ListUtils.checkList(sessions);
        sessions.get().forEach((i) -> deleteSession(i.getId(), status));
        return true;
    }
}
