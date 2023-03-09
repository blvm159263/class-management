package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    UserDTO getUserById(long id);
}
