package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepo;

    @Override
    public UserDTO getUserById(boolean status, long id) {
        User user = userRepo.findByStatusAndId(status, id).orElseThrow(() -> new NotFoundException("Users not found with id: "+ id));
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUser(boolean status) {
        return userRepo.findAllByStatus(status).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
