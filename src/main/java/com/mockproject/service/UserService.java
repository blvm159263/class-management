package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.mapper.UserMapper;
import com.mockproject.entity.Role;

import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private static final Long SUPER_ADMIN = 1L;
    private static final Long CLASS_ADMIN = 2L;
    private static final Long TRAINER = 3L;
    private static final Long STUDENT = 4L;

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
    public UserDTO getUserById(Long id) {
        return UserMapper.INSTANCE.toDTO(repository.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAllUser(boolean status) {
        return repository.findAllByStatus(status).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
