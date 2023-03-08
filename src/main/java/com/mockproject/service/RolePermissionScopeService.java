package com.mockproject.service;

import com.mockproject.dto.PermissionDTO;
import com.mockproject.dto.RolePermissionScopeDTO;
import com.mockproject.entity.RolePermissionScope;
import com.mockproject.mapper.RolePermissionScopeMapper;
import com.mockproject.repository.PermissionRepository;
import com.mockproject.repository.RolePermissionScopeRepository;
import com.mockproject.service.interfaces.IRolePermissionScopeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RolePermissionScopeService implements IRolePermissionScopeService {

    private final RolePermissionScopeRepository repository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<RolePermissionScope> findAllByRole_Id(Long roleId) {
        return repository.findAllByRole_Id(roleId);
    }

    @Override
    public List<RolePermissionScope> updateRolePermissionScope(List<RolePermissionScopeDTO> listRolePermissionScopeDTO) {
        return repository.saveAll(listRolePermissionScopeDTO.stream().map(RolePermissionScopeMapper.INSTANCE::toEntity).collect(Collectors.toList()));
    }

    @Override
    public RolePermissionScope save(RolePermissionScopeDTO rolePermissionScopeDTO) {
        return repository.save(RolePermissionScopeMapper.INSTANCE.toEntity(rolePermissionScopeDTO));
    }

    @Override
    public RolePermissionScopeDTO updateRolePermissionScopeByPermissionNameAndRoleIdAndScopeId(String permissionName, long roleId, long scopeId) {

        long permissionId = permissionRepository.getPermissionsByPermissionName(permissionName).getId();

        RolePermissionScopeDTO rolePermissionScopeDTO = RolePermissionScopeMapper.INSTANCE.toDTO(repository.findByRoleIdAndAndPermissionScopeId(roleId, permissionId));

        rolePermissionScopeDTO.setPermissionId(permissionId);

        repository.save(RolePermissionScopeMapper.INSTANCE.toEntity(rolePermissionScopeDTO));

        return null;
    }


}
