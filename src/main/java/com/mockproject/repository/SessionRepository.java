package com.mockproject.repository;

import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findBySyllabus(Syllabus syllabus);
}
