package com.mockproject.service;

import com.mockproject.dto.mapper.SessionDTOMapper;
import com.mockproject.entity.Session;
import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository repository;

    private final SessionDTOMapper mapper;
}
