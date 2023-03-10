package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getTrainerByClassCode(String code);

    UserDTO getCreatorByClassCode(String code);

    UserDTO getReviewerByClassCode(String code);

    UserDTO getApproverByClassCode(String code);
}
