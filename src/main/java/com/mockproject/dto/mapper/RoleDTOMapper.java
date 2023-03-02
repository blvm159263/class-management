package com.mockproject.dto.mapper;


import com.mockproject.dto.RoleDTO;
import com.mockproject.entity.Role;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RoleDTOMapper implements Function<Role, RoleDTO> {

    @Override
    public RoleDTO apply(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRoleName(),
                role.isStatus()
        );
    }
}
