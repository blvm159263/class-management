package com.mockproject.service.interfaces;

import com.mockproject.dto.PermissionScopeDTO;
import com.mockproject.dto.RolePermissionScopeDTO;
import com.mockproject.entity.PermissionScope;
import com.mockproject.entity.Role;

import java.util.List;

public interface IPermissionScopeService {
    public Long getPermissionScopeIdByPermissionScopeName(String name);

    public List<PermissionScopeDTO> getAll();

}
