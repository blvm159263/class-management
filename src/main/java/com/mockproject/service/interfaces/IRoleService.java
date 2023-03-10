package com.mockproject.service.interfaces;

import com.mockproject.dto.RoleDTO;
import com.mockproject.entity.Role;

import java.util.List;

public interface IRoleService {
    List<RoleDTO> getAll();

    List<Role> updateListRole (List<RoleDTO> listRoleDTO);

    Role save(RoleDTO roleDTO);
}
