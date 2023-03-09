package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private static final long SUPER_ADMIN = 1;
    private static final long CLASS_ADMIN = 2;
    private static final long TRAINER = 3;
    private static final long STUDENT = 4;

    private final UserRepository repository;

    @Override
    public List<UserDTO> listClassAdminTrue() {
        Role role = new Role();
        role.setId(CLASS_ADMIN);
        return repository.findByRoleAndStatus(role,true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listTrainerTrue() {
        Role role = new Role();
        role.setId(TRAINER);
        return repository.findByRoleAndStatus(role,true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(long id) {
        return repository.findById(id).map(UserMapper.INSTANCE::toDTO).orElse(null);
    }
}
