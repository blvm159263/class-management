package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;
import com.mockproject.dto.UserDTOCustom;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> csvToUsers(InputStream is, Boolean replace, Boolean skip);

    String readCSVFile(File file);

    List<UserDTOCustom> getAllByPageAndRowPerPage(Long page, Long rowPerPage);
    List<UserDTO> getAll();

    Page<UserDTO> searchByFilter(List<String> search, LocalDate dob, Boolean gender,  List<Long> atendeeId, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

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

    int getStateIdByStateName(String name);

    InputStream getCSVUserFileExample();

    void storeListUser(List<User> list);
}
