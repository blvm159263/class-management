package com.mockproject.service.interfaces;

import com.mockproject.dto.*;
import com.mockproject.entity.TrainingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ITrainingClassService {
    List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);

    List<TrainingClass> findAllBySpecification(Specification specification);

    List<TrainingClass> findAllBySearchTextAndDate(List<String> searchText,LocalDate date);

    List<TrainingClass> findAllBySearchTextAndWeek(List<String> searchText, LocalDate startDate,LocalDate endDate);


    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        long fsu, long trainerId, String search, String[] sort, Optional<Integer> page);

    List<TrainingClassDTO> getAllClass();

    Long create(TrainingClassDTO trainingClassDTO);


    TrainingClassDTO getAllDetails(long id);


}
