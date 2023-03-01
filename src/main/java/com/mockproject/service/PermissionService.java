package com.mockproject.service;

import com.mockproject.service.interfaces.IPermissionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionService implements IPermissionService {
}
