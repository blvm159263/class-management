package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {

    public UserDTO getUserById(boolean status, long id);

    public List<UserDTO> getAllUser(boolean status);
}
