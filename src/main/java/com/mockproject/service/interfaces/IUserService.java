package com.mockproject.service.interfaces;

import com.mockproject.dto.SearchUserFillerDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> getAll();

    List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage);

    Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

    public boolean updateStatus(long id);
    public Integer updateStateToFalse(long id);
    public Integer updateStateToTrue(long id);
    public boolean changeRole(long id, long roleId);
    public boolean editName(long id, String name);
    public boolean editDoB(long id, LocalDate date);
    public boolean editGender(long id, boolean gender);
    public boolean editLevel(long id, String levelCode);
}
