package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    UserDTO getUserById(Long id);

    public UserDTO getUserById(boolean status, long id);

    public List<UserDTO> getAllUser(boolean status);
}
