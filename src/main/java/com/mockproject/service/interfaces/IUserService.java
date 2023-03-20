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
    List<UserDTO> getAll();

    Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

    boolean updateStatus(Long id);

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    Integer updateStateToFalse(Long id);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUser(boolean status);

    Integer updateStateToTrue(Long id);

    boolean changeRole(Long id, Long roleId);

    boolean editName(Long id, String name);

    boolean editDoB(Long id, LocalDate date);

    boolean editGender(Long id, boolean gender);

    boolean editLevel(Long id, String levelCode);

    boolean editUser(UserDTO user);

    void encodePassword();

    UserDTO getUserById(boolean status, Long id);
}
