package com.mockproject.repository;

import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findById(Long id);

    public List<User> findAllBy();

    @Query(value = "SELECT * FROM tblUser\n" +
            "ORDER BY ID\n" +
            "OFFSET ((?1-1)*?2) ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
    public List<User> getAllByPageAndRowPerPage(long page, long rowPerPage);

    public Long countAllBy();



}
