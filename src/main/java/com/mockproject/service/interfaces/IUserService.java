package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;


public interface IUserService {
    List<UserDTO> getAll();

    Long countAllBy();

    List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage);



}
