package com.mockproject.service;

import com.mockproject.dto.mapper.UserDTOMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    private final UserDTOMapper mapper;
}