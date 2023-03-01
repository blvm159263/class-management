package com.mockproject.repository;

import com.mockproject.entity.RolePermissionScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionScopeRepository extends JpaRepository<RolePermissionScope, Long> {
}
