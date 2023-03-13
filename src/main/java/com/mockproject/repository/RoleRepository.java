package com.mockproject.repository;

import com.mockproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> getRoleById(long id);
    public Optional<Role> getRoleByRoleName(String roleName);
}
