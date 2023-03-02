package com.mockproject.service;

import com.mockproject.dto.mapper.RoleDTOMapper;
import com.mockproject.repository.RoleRepository;
import com.mockproject.service.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository repository;

    private final RoleDTOMapper mapper;
}
