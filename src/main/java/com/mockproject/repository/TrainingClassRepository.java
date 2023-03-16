package com.mockproject.repository;

import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long>, JpaSpecificationExecutor<TrainingClass> {

    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);

    @Query("SELECT tc FROM TrainingClass tc " +
            "LEFT JOIN tc.listClassSchedules cs " +
            "LEFT JOIN tc.listTrainingClassAdmins ta " +
            "LEFT JOIN tc.listTrainingClassUnitInformations ti "+
            "LEFT JOIN tc.location lo " +
            "LEFT JOIN tc.attendee at " +
            "where " +
            "(tc.className LIKE (:SearchText) OR " +
            "tc.classCode LIKE (:SearchText)  OR " +
            "tc.fsu.fsuName LIKE (:SearchText) OR " +
            "ta.admin.fullName LIKE (:SearchText) OR " +
            "at.attendeeName LIKE (:SearchText) OR " +
            "ti.trainer.fullName LIKE (:SearchText) OR "+
            "lo.locationName LIKE (:SearchText)) AND " +
            "cs.date = :date AND " +
            "tc.status = true ")
    public List<TrainingClass> findAllBySearchTextAndListClassSchedulesDate(String SearchText,LocalDate date);

    @Query("SELECT tc FROM TrainingClass tc " +
            "LEFT JOIN tc.listClassSchedules cs " +
            "LEFT JOIN tc.listTrainingClassAdmins ta " +
            "LEFT JOIN tc.location lo " +
            "LEFT JOIN tc.listTrainingClassUnitInformations ti "+
            "LEFT JOIN tc.attendee at " +
            "where " +
            "(tc.className LIKE (:searchText) OR " +
            "tc.classCode LIKE (:searchText)  OR " +
            "tc.fsu.fsuName LIKE (:searchText) OR " +
            "ta.admin.fullName LIKE (:searchText) OR " +
            "at.attendeeName LIKE (:searchText) OR " +
            "ti.trainer.fullName LIKE (:searchText) OR "+
            "lo.locationName LIKE (:searchText)) AND " +
            "cs.date BETWEEN :startDate AND :endDate AND " +
            "tc.status = true ")
    public List<TrainingClass> findAllBySearchTextAndListClassSchedulesWeek(String searchText,LocalDate startDate,LocalDate endDate);


}
