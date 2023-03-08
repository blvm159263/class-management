package com.mockproject.service;

import com.mockproject.entity.User;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public User getUserById(long id){
        return userRepository.findById(id).get();
    }
}
