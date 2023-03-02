package com.mockproject.dto.mapper;


import com.mockproject.dto.RolePermissionScopeDTO;
import com.mockproject.entity.RolePermissionScope;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RolePermissionScopeDTOMapper implements Function<RolePermissionScope, RolePermissionScopeDTO> {

    @Override
    public RolePermissionScopeDTO apply(RolePermissionScope rolePermissionScope) {
        return new RolePermissionScopeDTO(
                rolePermissionScope.getId(),
                rolePermissionScope.isStatus(),
                rolePermissionScope.getRole().getId(),
                rolePermissionScope.getPermission().getId(),
                rolePermissionScope.getPermissionScope().getId()
        );
    }
}
