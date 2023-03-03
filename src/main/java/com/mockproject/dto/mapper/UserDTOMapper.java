package com.mockproject.dto.mapper;


import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getImage(),
                user.getState(),
                user.getDob(),
                user.getPhone(),
                user.isGender(),
                user.isStatus(),
                user.getRole().getId(),
                user.getLevel().getId(),
                user.getAttendee().getId()
        );
    }
}
