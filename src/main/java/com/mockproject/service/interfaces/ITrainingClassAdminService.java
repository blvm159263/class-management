package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface ITrainingClassAdminService {
    List<UserDTO> getAdminByClassId(long id);
}
