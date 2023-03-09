package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    public List<Session> getAllSessionBySyllabusId(long syllabusId, boolean status) {
        List<Session> listSession = sessionRepository.findSessionBySyllabusIdAndStatus(syllabusId, status);
        return listSession;
    }

    public boolean createSession(long syllabusId, List<SessionDTO> listSession){
        listSession.forEach((i) ->
        {
            i.setSyllabusId(syllabusId);
            sessionRepository.save(SessionMapper.INSTANCE.toEntity(i));
        });
        return true;
    }

    public Session editSession(long id, long syllabusId, SessionDTO sessionDTO){
        Optional<Session> session = Optional.ofNullable(sessionRepository.findSessionBySyllabusIdAndStatusAndId(syllabusId, true, id));
        session.orElseThrow(() -> new RuntimeException("Session doesn't exist."));
        sessionDTO.setId(id);
        sessionDTO.setSyllabusId(syllabusId);
        Session updateSession = sessionRepository.save(SessionMapper.INSTANCE.toEntity(sessionDTO));
        return updateSession;
    }
}
