package com.mockproject.mapper;

import com.mockproject.dto.RolePermissionScopeDTO;
import com.mockproject.entity.Permission;
import com.mockproject.entity.PermissionScope;
import com.mockproject.entity.Role;
import com.mockproject.entity.RolePermissionScope;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RolePermissionScopeMapper {

    RolePermissionScopeMapper INSTANCE = Mappers.getMapper(RolePermissionScopeMapper.class);


    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "permissionScopeId", source = "permissionScope.id")
    @Mapping(target = "permissionId", source = "permission.id")
    RolePermissionScopeDTO toDTO(RolePermissionScope permissionScope);


    @Mapping(target = "role", source = "roleId", qualifiedByName = "mapRole")
    @Mapping(target = "permissionScope", source = "permissionScopeId", qualifiedByName = "mapPermissionScope")
    @Mapping(target = "permission", source = "permissionId", qualifiedByName = "mapPermission")
    RolePermissionScope toEntity(RolePermissionScopeDTO permissionScope);

    @Named("mapPermission")
    default Permission mapPermission(long id){
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }
    @Named("mapPermissionScope")
    default PermissionScope mapPermissionScope(long id){
        PermissionScope permissionScope = new PermissionScope();
        permissionScope.setId(id);
        return permissionScope;
    }
    @Named("mapRole")
    default Role mapRole(long id){
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
