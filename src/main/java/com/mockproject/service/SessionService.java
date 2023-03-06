package com.mockproject.service;

import com.mockproject.entity.Session;
import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
}
