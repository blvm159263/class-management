package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    Syllabus getSyllabusById(Long id);
    List<Syllabus> getAllSyllabusByIdInAndStatus(List<Long> id,boolean status);

}
