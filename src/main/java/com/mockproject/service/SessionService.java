package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    @Override
    public List<SessionDTO> getSessionListBySyllabusId(Long idSyllabus){
        return sessionRepository.getSessionListBySyllabusId(idSyllabus).stream().map(SessionMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
