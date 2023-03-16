package com.mockproject.repository;

import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoleAndStatus(Role role, boolean status);

    Optional<User> findByEmailAndStatus(String email, boolean status);

    Optional<User> findByStatusAndId(boolean status, long id);

    List<User> findAllByStatus(boolean status);

    Optional<User> findByIdAndStatus(long id, Boolean status);

}
