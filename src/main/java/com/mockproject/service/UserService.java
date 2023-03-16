package com.mockproject.service;

import com.mockproject.dto.SearchUserFillerDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<UserDTO> searchByFillter(SearchUserFillerDTO userFillerDTO) {


        return repository.searchByFiller(userFillerDTO.getId(), userFillerDTO.getDob(), userFillerDTO.getEmail() , userFillerDTO.getFullname(), userFillerDTO.getGender(), userFillerDTO.getPhone(), userFillerDTO.getState(), userFillerDTO.getAttendee_id(), userFillerDTO.getLevel_id(), userFillerDTO.getRole_id())
                .stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> searchByRoleId(List<Long> listRole, Optional<Integer> page, Optional<Integer> size){
        int page1 = 0;
        int size1 = 10;
        boolean listIsNull = false;
        if (page.isPresent()) page1 = page.get();
        if (size.isPresent()) size1 = size.get();
        if (listRole == null || listRole.isEmpty()) {
            listRole = new ArrayList<>();
        }
        List<Sort.Order> order = new ArrayList<>();
        order.add(new Sort.Order(Sort.Direction.ASC,"dob"));
        Pageable pageable = PageRequest.of(page1, size1, Sort.by(order));
        List<Object> list = new ArrayList<>();

        Page<User> pages = repository.searchByRoleId(listRole, pageable);
        return new PageImpl<>(
                pages.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                pages.getPageable(),
                pages.getTotalElements());
    }

    public void encodePassword(){
        for (User user: repository.findAllBy()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }
    }
}
