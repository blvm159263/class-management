package com.mockproject.repository;

import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoleAndStatus(Role role, boolean status);
}
