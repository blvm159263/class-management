package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;
import com.mockproject.dto.UserDTOCustom;
import org.springframework.data.domain.Page;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    String readCSVFile(File file);

    List<UserDTOCustom> getAllByPageAndRowPerPage(long page, long rowPerPage);

    Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

    boolean updateStatus(long id);

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    Integer updateStateToFalse(long id);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUser(boolean status);

    Integer updateStateToTrue(long id);

    boolean changeRole(long id, long roleId);

    boolean editName(long id, String name);

    boolean editDoB(long id, LocalDate date);

    boolean editGender(long id, boolean gender);

    boolean editLevel(long id, String levelCode);

    boolean editUser(UserDTO user);

    void encodePassword();

    UserDTO getUserById(boolean status, long id);
}
