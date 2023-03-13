package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionScopeDTO implements Serializable {
    public RolePermissionScopeDTO(boolean status, long roleId, long permissionId, long permissionScopeId) {
        this.status = status;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.permissionScopeId = permissionScopeId;
    }

    private Long id;
    private boolean status;
    private Long roleId;
    private Long permissionId;
    private Long permissionScopeId;
}
