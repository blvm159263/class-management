package com.mockproject.service;

import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public User get(long id) {
        return repository.getReferenceById(id);
    }

}
