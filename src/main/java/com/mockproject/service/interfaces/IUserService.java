package com.mockproject.service.interfaces;

import com.mockproject.dto.SearchUserFillerDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;

import java.util.List;

public interface IUserService {
    UserDTO saveUser(UserDTO userData);

    UserDTO getByID(long id);

    List<UserDTO> getAll();

    Long countAllBy();

    List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage);

    List<UserDTO> searchByFillter(SearchUserFillerDTO userFillerDTO);

}
