package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO getUserById(boolean status, long id);

    List<UserDTO> getAllUser(boolean status);
}
