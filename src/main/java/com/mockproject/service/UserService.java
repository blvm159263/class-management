package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.LevelRepository;
import com.mockproject.repository.RoleRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final LevelRepository levelRepository;

    @Override
    public UserDTO findByFullNameContains(String fullName) {
        UserDTO userDTO = new UserDTO();
        userDTO = userMapper.toDTO(repository.findByFullNameContains(fullName).get());
        if (userDTO != null) return userDTO;
        else return null;
    }

    @Override
    public boolean updateStatus(long id) {
        boolean status = false;
        Optional<User> user = repository.findById(id);
        if (user.isPresent()){
            User u = user.get();
            u.setStatus(false);
            repository.save(u);
            status = true;
        }
        return status;
    }

    @Override
    public Integer updateStateToFalse(long id) {
        Optional<User> user = repository.findById(id);
        int state = -1;
        if (user.isPresent()){
            User u = user.get();
            u.setState(0);
            repository.save(u);
            state = 0;
        }
        return state;
    }

    @Override
    public Integer updateStateToTrue(long id) {
        Optional<User> user = repository.findById(id);
        int state = -1;
        if (user.isPresent()){
            User u = user.get();
            u.setState(1);
            repository.save(u);
            state = 1;
        }
        return state;
    }

    @Override
    public boolean changeRole(long id, long roleId) {
        Optional<User> user = repository.findById(id);
        Optional<Role> role = roleRepository.getRoleById(roleId);
        if (user.isPresent()  && role.isPresent()){
            User user1 = user.get();
            Role role1 = role.get();
            user1.setRole(role1);
            repository.save(user1);
            return true;
        }
        return false;
    }

    @Override
    public boolean editName(long id, String name) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()){
            User u = user.get();
            u.setFullName(name);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editDoB(long id, LocalDate date) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()){
            User u = user.get();
            u.setDob(date);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editGender(long id, boolean gender) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()){
            User u = user.get();
            u.setGender(gender);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editLevel(long id, String levelCode) {
        Optional<User> user = repository.findById(id);
        Optional<Level> level = levelRepository.getLevelByLevelCode(levelCode);
        if (user.isPresent()  && level.isPresent()){
            User user1 = user.get();
            Level level1 = level.get();
            user1.setLevel(level1);
            repository.save(user1);
            return true;
        }
        return false;
    }

}
