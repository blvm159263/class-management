package com.mockproject.repository;

import com.mockproject.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findSessionBySyllabusIdAndStatus(long syllabusID, boolean status);
}
