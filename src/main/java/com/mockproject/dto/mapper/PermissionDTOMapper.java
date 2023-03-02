package com.mockproject.dto.mapper;


import com.mockproject.dto.PermissionDTO;
import com.mockproject.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
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
