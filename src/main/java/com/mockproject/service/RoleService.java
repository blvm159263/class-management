package com.mockproject.service;

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
    public String getRoleNameById(long id){
        String role = repository.getRoleById(id).get().getRoleName();
        return role;
    }

    @Override
    public long getRoleByRoleName(String roleName) {
        long roleId = repository.getRoleByRoleName(roleName).get().getId();
        return roleId;
    }

}
