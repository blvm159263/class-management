package com.mockproject.repository;

import com.mockproject.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<List<Session>> findBySyllabusIdAndStatus(long syllabusID, boolean status);

    Optional<Session> findByIdAndStatus(long id, boolean status);

    List<Session> getSessionListBySyllabusId(long idSyllabus);
}
