package com.mockproject.dto.mapper;


import com.mockproject.dto.PermissionDTO;
import com.mockproject.entity.Permission;

import java.util.function.Function;

public class PermissionDTOMapper implements Function<Permission, PermissionDTO> {

    @Override
    public PermissionDTO apply(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getPermissionName(),
                permission.isStatus()
        );
    }
}
