package com.mockproject.service;

import com.mockproject.dto.mapper.RolePermissionScopeDTOMapper;
import com.mockproject.repository.RolePermissionScopeRepository;
import com.mockproject.service.interfaces.IRolePermissionScopeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RolePermissionScopeService implements IRolePermissionScopeService {

    private final RolePermissionScopeRepository repository;

    private final RolePermissionScopeDTOMapper mapper;
}
