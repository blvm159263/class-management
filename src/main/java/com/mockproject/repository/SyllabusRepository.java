package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    Optional<Syllabus> findByIdAndStatus(long id, boolean status);

    Optional<List<Syllabus>> findByStateAndStatus(boolean state, boolean status);

    Optional<List<Syllabus>> findAllByStatus(boolean status);

    Optional<Syllabus> findByIdAndStateAndStatus(long syllabusId, boolean state, boolean status);
}
