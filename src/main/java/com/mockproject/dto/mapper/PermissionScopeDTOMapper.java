package com.mockproject.dto.mapper;


import com.mockproject.dto.PermissionScopeDTO;
import com.mockproject.entity.PermissionScope;

import java.util.function.Function;

public class PermissionScopeDTOMapper implements Function<PermissionScope, PermissionScopeDTO> {

    @Override
    public PermissionScopeDTO apply(PermissionScope permissionScope) {
        return new PermissionScopeDTO(
                permissionScope.getId(),
                permissionScope.getScopeName(),
                permissionScope.isStatus()
        );
    }
}
