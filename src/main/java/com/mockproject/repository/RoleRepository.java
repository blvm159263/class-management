package com.mockproject.repository;

import com.mockproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByRoleName(String name);

    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByRoleName(String roleName);

    Optional<Role> getRoleByIdIsNotAndRoleName(Long id, String name);
}
