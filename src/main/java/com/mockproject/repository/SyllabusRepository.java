package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    List<Syllabus> findByStatus(boolean status);

    @Query("SELECT s FROM Syllabus s " +
            "WHERE s.status = ?1 " +
            "AND (?2 IS NULL OR ?3 IS NULL OR s.dateCreated BETWEEN ?2 AND ?3) " +
            "AND (s.name LIKE '%' + ?4 + '%' OR s.code LIKE '%' + ?4 + '%' " +
            "OR s.creator.fullName LIKE '%' + ?4 + '%' OR s.id IN ?5)")
    Page<Syllabus> getListSyllabus(boolean status,
                                   LocalDate fromDate, LocalDate toDate,
                                   String search, List<Long> syllabusIdList, Pageable pageable);
    Optional<Syllabus> findByIdAndStatus(Long id, boolean status);

    Optional<List<Syllabus>> findByStateAndStatus(boolean state, boolean status);

    Optional<List<Syllabus>> findAllByStatus(boolean status);

    Optional<Syllabus> findByIdAndStateAndStatus(Long syllabusId, boolean state, boolean status);

    Syllabus getSyllabusById(Long id);
}
