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
            "AND (:#{#locationId.size()} = 0 OR c.location.id IN ?2) " +
            "AND (?3 IS NULL OR ?4 IS NULL OR c.startDate BETWEEN ?3 AND ?4) " +
            "AND (:#{#period.size()} = 0 OR c.period IN ?5) " +
            "AND c.attendee.attendeeName LIKE '%' + ?6 + '%' " +
            "AND c.state LIKE '%' + ?7 + '%' " +
            "AND (:#{#attendeeId.size()} = 0 OR c.attendee.id IN ?8) " +
            "AND (?9 = 0 OR c.fsu.id = ?9) " +
            "AND (:#{#classId.size()} = 0 OR c.id IN ?10) " +
            "AND (c.className LIKE '%' + ?11 + '%' OR c.classCode LIKE '%' + ?11 + '%' " +
            "OR c.creator.fullName LIKE '%' + ?11 + '%')")
    Page<TrainingClass> getListClass(boolean status, List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                     List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                     long fsu, List<Long> classId, String search, Pageable page);

    List<TrainingClass> findAllByStatus(boolean status);
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);

    @Query("SELECT tc FROM TrainingClass tc " +
            "LEFT JOIN tc.listClassSchedules cs " +
            "LEFT JOIN tc.listTrainingClassAdmins ta " +
            "LEFT JOIN tc.location lo " +
            "LEFT JOIN tc.attendee at " +
            "where " +
            "(tc.className IN (:listSearchText) OR " +
            "tc.classCode IN (:listSearchText)  OR " +
            "tc.fsu.fsuName IN (:listSearchText) OR " +
            "ta.admin.fullName IN (:listSearchText) OR " +
            "at.attendeeName IN (:listSearchText) OR " +
            "lo.locationName IN (:listSearchText)) AND " +
            "cs.date = :date")
    List<TrainingClass> findAllBySearchTextAndListClassSchedulesDate(List<String> listSearchText, LocalDate date);

    @Query("SELECT tc FROM TrainingClass tc " +
            "LEFT JOIN tc.listClassSchedules cs " +
            "LEFT JOIN tc.listTrainingClassAdmins ta " +
            "LEFT JOIN tc.location lo " +
            "LEFT JOIN tc.attendee at " +
            "where " +
            "(tc.className IN (:listSearchText) OR " +
            "tc.classCode IN (:listSearchText)  OR " +
            "tc.fsu.fsuName IN (:listSearchText) OR " +
            "ta.admin.fullName IN (:listSearchText) OR " +
            "at.attendeeName IN (:listSearchText) OR " +
            "lo.locationName IN (:listSearchText)) AND " +
            "cs.date BETWEEN :startDate AND :endDate")
    List<TrainingClass> findAllBySearchTextAndListClassSchedulesWeek(List<String> listSearchText, LocalDate startDate, LocalDate endDate);
}
