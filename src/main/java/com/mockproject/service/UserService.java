package com.mockproject.service;

import com.mockproject.entity.User;
import com.mockproject.repository.RoleRepository;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.LevelRepository;
import com.mockproject.repository.RoleRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
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
    public Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception {
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
    public boolean editUser(UserDTO user) {
        Optional<User> user1 = repository.findById(user.getId());
        Optional<Level> level = levelRepository.getLevelById(user.getLevelId());
        if (user1.isPresent()){
            User u = user1.get();
            Level level1 = level.get();
            u.setFullName(user.getFullName());
            u.setDob(user.getDob());
            u.setGender(user.isGender());
            u.setLevel(level1);
            repository.save(u);
            return true;
        }
        return false;
    }




    @Override
    public String readCSVFile(File file, String scanning, String duplicateHandle) {
        List<User> userList = new ArrayList<>();
        try {
            if (!file.exists()) {
                return "File not Found!";
            } else {
                // create a reader for the CSV file
                CSVReader reader = new CSVReader(new FileReader(file));

                // read the header row
                String[] headerRow = reader.readNext();

                // read the data rows and map them to objects
                String[] rowData;
                while ((rowData = reader.readNext()) != null) {
                    User user = new User();
                    user.setEmail(rowData[0]);
                    user.setPassword(passwordEncoder.encode("123456"));
                    user.setFullName(rowData[1]);
                    if (rowData[2].equals("Nam")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    user.setDob(LocalDate.parse(rowData[3],formatter));
                    user.setPhone(rowData[4]);
                    user.setStatus(true);

                    if(scanning.toLowerCase().equals("fullname")){
                        if(duplicateHandle.toLowerCase().equals("replace")){
                            User user1 = repository.findByFullName(user.getFullName()).get();
                            if(user1 != null){
                                user1.setEmail(user.getEmail());
                                user1.setGender(user.isGender());
                                user1.setDob(user.getDob());
                                user1.setPhone(user.getPhone());
                                repository.save(user1);
                            }else{
                                repository.save(user);
                            }
                        }
                    }else{
                        if(duplicateHandle.toLowerCase().equals("replace")){
                            User user1 = repository.findByEmail(user.getEmail()).get();
                            if(user1 != null){
                                user1.setEmail(user.getEmail());
                                user1.setGender(user.isGender());
                                user1.setDob(user.getDob());
                                user1.setPhone(user.getPhone());
                                repository.save(user1);
                            }else{
                                repository.save(user);
                            }
                        }
                    }
                    userList.add(user);
                }
                reader.close();
            }

        } catch (Exception e) {
            return String.valueOf(e);
        }
        return "Done!!!";

    }
}
