package com.mockproject.service.interfaces;

import com.mockproject.dto.UserDTO;

import java.util.List;

public interface ITrainingClassAdminService {

    boolean saveList(List<Long> adminId, Long tcId);
}
