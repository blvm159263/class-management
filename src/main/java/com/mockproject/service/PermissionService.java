package com.mockproject.service;

import com.mockproject.dto.mapper.PermissionDTOMapper;
import com.mockproject.repository.PermissionRepository;
import com.mockproject.service.interfaces.IPermissionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository repository;

    private final PermissionDTOMapper mapper;
}
