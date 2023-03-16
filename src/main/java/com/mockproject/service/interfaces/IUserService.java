package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getTrainerByClassId(long id);

    List<UserDTO> getTrainerOnThisDayById(long id, int day);

    UserDTO getCreatorByClassId(long id);

    UserDTO getReviewerByClassId(long id);

    UserDTO getApproverByClassId(long id);
}
