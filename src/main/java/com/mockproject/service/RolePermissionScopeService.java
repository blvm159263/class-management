package com.mockproject.service;

import com.mockproject.repository.RolePermissionScopeRepository;
import com.mockproject.service.interfaces.IRolePermissionScopeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RolePermissionScopeService implements IRolePermissionScopeService {

    private final RolePermissionScopeRepository repository;

}
