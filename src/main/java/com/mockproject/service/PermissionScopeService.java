package com.mockproject.service;

import com.mockproject.service.interfaces.IPermissionScopeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionScopeService implements IPermissionScopeService {
}
