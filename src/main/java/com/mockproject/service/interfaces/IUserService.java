package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;

import java.util.List;

public interface IUserService {
    User get(Long id);

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    UserDTO getUserById(Long id);
}
