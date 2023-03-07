package com.mockproject.service;

import com.mockproject.repository.PermissionRepository;
import com.mockproject.service.interfaces.IPermissionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository repository;

}
