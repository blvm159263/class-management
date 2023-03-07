package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    Syllabus findByIdAndStateAndStatus(long id, boolean state, boolean status);

    List<Syllabus> findByStateAndStatus(boolean state, boolean status);
}
