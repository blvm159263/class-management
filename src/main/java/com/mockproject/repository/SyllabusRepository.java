package com.mockproject.repository;

import com.mockproject.entity.Syllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

//    Page<Syllabus> findAllByStatusAndNameContainsIgnoreCaseOrStatusAndCodeContainsIgnoreCaseOrStatusAndCreator

    @Query("SELECT s FROM Syllabus s " +
            "WHERE s.status = ?1 " +
            "AND s.dateCreated BETWEEN ?2 AND ?3 " +
            "AND (s.name LIKE '%'+ ?4 + '%' OR s.code LIKE '%'+ ?4 + '%' " +
            "OR s.creator.fullName LIKE '%'+ ?4 + '%' OR s.id IN ?5)")
    Page<Syllabus> getListSyllabus(boolean status,
                                   LocalDate fromDate, LocalDate toDate,
                                   String search, List<Long> syllabusIdList, Pageable pageable);

}
