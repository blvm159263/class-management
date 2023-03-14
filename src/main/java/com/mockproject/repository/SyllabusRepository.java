package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    Syllabus getSyllabusById(long id);
}
