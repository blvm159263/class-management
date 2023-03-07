package com.mockproject.service;

import com.mockproject.repository.SessionRepository;
import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository repository;

}
