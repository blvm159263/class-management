package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository repository;

    @Override
    public List<SessionDTO> listBySyllabus(long sid) {
        Syllabus syllabus = new Syllabus();
        syllabus.setId(sid);
        return repository.findBySyllabus(syllabus).stream().map(SessionMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
