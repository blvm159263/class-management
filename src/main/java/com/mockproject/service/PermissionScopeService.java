package com.mockproject.service;

import com.mockproject.dto.PermissionScopeDTO;
import com.mockproject.mapper.PermissionScopeMapper;
import com.mockproject.repository.PermissionScopeRepository;
import com.mockproject.service.interfaces.IPermissionScopeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionScopeService implements IPermissionScopeService {

    private final PermissionScopeRepository repository;

    @Override
    public Long getPermissionScopeIdByPermissionScopeName(String name) {
        return repository.getPermissionScopeByScopeName(name).getId();
    }

    @Override
    public List<PermissionScopeDTO> getAll() {
        return repository.findAll().stream().map(PermissionScopeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
