package com.mockproject.service;

import com.mockproject.service.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService implements IRoleService {
}
