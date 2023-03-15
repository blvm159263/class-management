package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.entity.TrainingClass;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;
import com.mockproject.dto.TrainingClassDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

public interface ITrainingClassService {
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);

    public List<TrainingClass> findAllBySpecification(Specification specification);

    public List<TrainingClass> findAllBySearchTextAndDate(List<String> searchText,LocalDate date);

    public List<TrainingClass> findAllBySearchTextAndWeek(List<String> searchText, LocalDate startDate,LocalDate endDate);


    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        long fsu, long trainerId, String search, String[] sort, Optional<Integer> page);

    List<TrainingClassDTO> getAllClass();
}
