package com.mockproject.service;

import com.mockproject.service.interfaces.ISessionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SessionService implements ISessionService {
}
