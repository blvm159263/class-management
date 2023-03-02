package com.mockproject.service;

import com.mockproject.dto.mapper.PermissionScopeDTOMapper;
import com.mockproject.repository.PermissionScopeRepository;
import com.mockproject.service.interfaces.IPermissionScopeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class PermissionScopeService implements IPermissionScopeService {

    private final PermissionScopeRepository repository;

    private final PermissionScopeDTOMapper mapper;
}
