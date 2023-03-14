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
            "(tc.className IN (:listSearchText) OR " +
            "tc.classCode IN (:listSearchText)  OR " +
            "tc.fsu.fsuName IN (:listSearchText) OR " +
            "ta.admin.fullName IN (:listSearchText) OR " +
            "at.attendeeName IN (:listSearchText) OR " +
            "ti.trainer.fullName in (:listSearchText) OR "+
            "lo.locationName IN (:listSearchText)) AND " +
            "cs.date = :date")
    public List<TrainingClass> findAllBySearchTextAndListClassSchedulesDate(List<String> listSearchText,LocalDate date);

    @Query("SELECT tc FROM TrainingClass tc " +
            "LEFT JOIN tc.listClassSchedules cs " +
            "LEFT JOIN tc.listTrainingClassAdmins ta " +
            "LEFT JOIN tc.location lo " +
            "LEFT JOIN tc.listTrainingClassUnitInformations ti "+
            "LEFT JOIN tc.attendee at " +
            "where " +
            "(tc.className IN (:listSearchText) OR " +
            "tc.classCode IN (:listSearchText)  OR " +
            "tc.fsu.fsuName IN (:listSearchText) OR " +
            "ta.admin.fullName IN (:listSearchText) OR " +
            "at.attendeeName IN (:listSearchText) OR "+
            "ti.trainer.fullName in (:listSearchText) OR "+
            "lo.locationName IN (:listSearchText)) AND " +
            "cs.date BETWEEN :startDate AND :endDate")
    public List<TrainingClass> findAllBySearchTextAndListClassSchedulesWeek(List<String> listSearchText,LocalDate startDate,LocalDate endDate);


}
