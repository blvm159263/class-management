package com.mockproject.service;

import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {
}
