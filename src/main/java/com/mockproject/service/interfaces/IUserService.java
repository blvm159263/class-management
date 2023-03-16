package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> listClassAdminTrue();

    List<UserDTO> listTrainerTrue();

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUser(boolean status);

    List<UserDTO> getAllTrainersByTrainingClassId(long id);

    List<UserDTO> getAllAdminsByTrainingClassId(long id);

    UserDTO getCreatorByTrainingClassId(long id);

    List<UserDTO> getAllTrainersForADateByTrainingClassId(long id, int dayNth);
}
