package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getTrainerByClassCode(String code);

    List<UserDTO> getTrainerOntheDayById(long id, int day);

//    List<UserDTO> getTrainerById(long id);

    UserDTO getCreatorByClassCode(String code);

    UserDTO getReviewerByClassCode(String code);

    UserDTO getApproverByClassCode(String code);
}
