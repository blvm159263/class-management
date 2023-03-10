package com.mockproject.repository;

import com.mockproject.entity.PermissionScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionScopeRepository extends JpaRepository<PermissionScope, Long> {

    PermissionScope getPermissionScopeByScopeName(String name);

    PermissionScope getPermissionScopeById(long id);

}
