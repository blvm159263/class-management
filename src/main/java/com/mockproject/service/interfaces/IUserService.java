package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    String readCSVFile(File file);

    List<UserDTO> getAll();

    List<UserDTO> getAllByPageAndRowPerPage(Long page, Long rowPerPage);

    Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> roleId, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

    boolean updateStatus(Long id);

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    Integer updateStateToFalse(Long id);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUser(boolean status);

    Integer updateStateToTrue(Long id);
    List<UserDTO> getAllTrainersByTrainingClassId(long id);

    List<UserDTO> getAllAdminsByTrainingClassId(long id);

    UserDTO getCreatorByTrainingClassId(long id);

    List<UserDTO> getAllTrainersForADateByTrainingClassId(long id, int dayNth);

    Integer updateStateToTrue(long id);

    boolean changeRole(Long id, Long roleId);

    boolean editName(Long id, String name);

    boolean editDoB(Long id, LocalDate date);

    boolean editGender(Long id, boolean gender);

    boolean editLevel(Long id, String levelCode);

    boolean editUser(UserDTO user);

    void encodePassword();

    UserDTO getUserById(boolean status, Long id);
}
