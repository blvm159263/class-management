package com.mockproject.service;

import com.mockproject.dto.SearchUserFillerDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    @Override
    public UserDTO getByID(long id){
        Optional<User> u = repository.findById(id);
        if(!u.isPresent()) return null;
        User user = u.get();
        return UserMapper.INSTANCE.toDTO(user);
    }

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

    @Override
    public UserDTO saveUser(UserDTO userData) {
        return UserMapper.INSTANCE.toDTO(repository.save(UserMapper.INSTANCE.toEntity(userData)));
    }
}
