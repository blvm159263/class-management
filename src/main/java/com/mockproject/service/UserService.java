package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll(){
        return repository.findAllBy().stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Long countAllBy() {
        return repository.countAllBy();
    }

    @Override
    public List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage) {
        return repository.getAllByPageAndRowPerPage(page, rowPerPage).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }


    private final UserRepository userRepository;

    public User getUserById(long id){
        return userRepository.findById(id).get();
    }
    public void encodePassword(){
        for (User user: repository.findAllBy()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }
    }
}
