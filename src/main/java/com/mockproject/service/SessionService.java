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

import java.util.List;

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
}
