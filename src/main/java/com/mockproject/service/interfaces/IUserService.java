package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface IUserService {
    public UserDTO findByFullNameContains(String fullName);
    public boolean updateStatus(long id);
    public Integer updateStateToFalse(long id);
    public Integer updateStateToTrue(long id);
    public boolean changeRole(long id, long roleId);
    public boolean editName(long id, String name);
    public boolean editDoB(long id, LocalDate date);
    public boolean editGender(long id, boolean gender);
    public boolean editLevel(long id, String levelCode);
}
