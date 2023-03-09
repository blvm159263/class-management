package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    @Query("SELECT c FROM TrainingClass c " +
            "WHERE c.status = ?1 " +
            "AND c.location.id IN ?2 " +
            "AND c.startDate BETWEEN ?3 AND ?4 " +
            "AND c.period IN ?5 " +
            "AND c.attendee.attendeeName LIKE '%' + ?6 + '%' " +
            "AND c.state LIKE '%' + ?7 + '%' " +
            "AND c.attendee.id IN ?8 " +
            "AND c.fsu.fsuName LIKE '%' + ?9 + '%' " +
            "AND c.id IN ?10 " +
            "AND (c.className LIKE '%' + ?11 + '%' OR c.classCode LIKE '%' + ?11 + '%' " +
            "OR c.creator.fullName LIKE '%' + ?11 + '%')")
    Page<TrainingClass> getListClass(boolean status,
                                     List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                     List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                     String fsu, List<Long> classId, String search, Pageable page);

    List<TrainingClass> findAllByStatus(boolean status);
}
