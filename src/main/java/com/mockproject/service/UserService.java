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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final LevelRepository levelRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll() {
        return repository.findAllBy().stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }


    @Override
    public List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage) {

        return repository.getAllByPageAndRowPerPage(page, rowPerPage).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> searchByFillter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception {
        int page1 = 0;
        int size1 = 10;
        Pageable pageable;
        List<Sort.Order> order = new ArrayList<>();
        if (page.isPresent()) page1 = page.get() - 1;
        if (size.isPresent()) size1 = size.get();

        if (sort != null && !sort.isEmpty()) {
            for (String sortItem : sort) {
                String[] subSort = sortItem.split("-");
                order.add(new Sort.Order(getSortDirection(subSort[1]), subSort[0]));
            }
            pageable = PageRequest.of(page1, size1, Sort.by(order));
        } else {
            pageable = PageRequest.of(page1, size1);
        }
        Page<User> pages;
        try {
            pages = repository.searchByFiller(id, dob, email, fullName, gender, phone, stateId, atendeeId, levelId, role_id, pageable);
        } catch (Exception e) {
            throw e;
        }
        return new PageImpl<>(
                pages.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                pages.getPageable(),
                pages.getTotalElements());
    }

    public void encodePassword() {
        for (User user : repository.findAllBy()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }



    @Override
    public boolean updateStatus(long id) {
        boolean status = false;
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
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
        if (user.isPresent()) {
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
        if (user.isPresent()) {
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
        if (user.isPresent() && role.isPresent()) {
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
        if (user.isPresent()) {
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
        if (user.isPresent()) {
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
        if (user.isPresent()) {
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
        if (user.isPresent() && level.isPresent()) {
            User user1 = user.get();
            Level level1 = level.get();
            user1.setLevel(level1);
            repository.save(user1);
            return true;
        }
        return false;
    }

    @Override
    public boolean editEmail(long id, String email) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setEmail(email);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editImage(long id, String image) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setImage(image);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editPhone(long id, String phone) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setPhone(phone);
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public UserDTO getByID(long id){
        Optional<User> u = repository.findById(id);
        if(!u.isPresent()) return null;
        User user = u.get();
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public UserDTO saveUser(UserDTO userData) {
        Optional<User> o_user = repository.findById(userData.getId());
        if(o_user.isPresent()){
            User user = o_user.get();

            user.setImage(userData.getImage());
            user.setEmail(userData.getEmail());
            user.setFullName(userData.getFullName());
            user.setDob(userData.getDob());
            user.setGender(userData.isGender());
            user.setPhone(userData.getPhone());
            try {
                user.setRole(roleRepository.getRoleById(userData.getRoleId()).get());
            }catch(Exception e){
                System.out.println("Role does not exist!");
            }
            try {
                user.setLevel(levelRepository.getLevelById(userData.getLevelId()).get());
            }catch(Exception e){
                System.out.println("Level does not exist!");
            }
            user.setState(userData.getState());
            user.setStatus(userData.isStatus());
            return UserMapper.INSTANCE.toDTO(repository.save(user));
        }
        return null;
    }

}
