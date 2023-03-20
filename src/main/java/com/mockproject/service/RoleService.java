package com.mockproject.service;

import com.mockproject.dto.RoleDTO;
import com.mockproject.entity.Role;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.repository.RoleRepository;
import com.mockproject.service.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository repository;

    @Override
    public RoleDTO getRoleById(Long id){
        Optional<Role> role = repository.getRoleById(id);
        if (role.isPresent()){
            return RoleMapper.INSTANCE.toDTO(role.get());
        }
        return null;
    }

    @Override
    public Long getRoleByRoleName(String roleName) {
        Optional<Role> role = repository.getRoleByRoleName(roleName);
        if (role.isPresent()){
            return repository.getRoleByRoleName(roleName).get().getId();
        }
        else return null;
    }

    @Override
    public List<RoleDTO> getAll() {
        return repository.findAll().stream().map(RoleMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Role> updateListRole(List<RoleDTO> listRoleDTO) {
        return repository.saveAll(listRoleDTO.stream().map(RoleMapper.INSTANCE::toEntity).collect(Collectors.toList()));
    }

    @Override
    public Role save(RoleDTO roleDTO) {
        return repository.save(RoleMapper.INSTANCE.toEntity(roleDTO));
    }

    @Override
    public Boolean checkDuplicatedByRoleName(String name) {
        System.out.println(repository.findAllByRoleName(name).size() + "===================================================================");
        if (repository.findAllByRoleName(name).size() > 1){
            return true;
        } else
        return false;
    }

    @Override
    public Boolean checkDuplicatedByRoleIdAndRoleName(Long id, String name) {
        if (repository.getRoleByIdIsNotAndRoleName(id, name).isPresent()){
            return true;
        } return false;
    }

}
