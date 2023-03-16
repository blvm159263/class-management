package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.entity.TrainingClass;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public interface ITrainingClassService {
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);
    public List<TrainingClass> findAllBySpecification(Specification specification);

    public List<TrainingClass> findAllBySearchTextAndDate(String searchText,LocalDate date);

    public List<TrainingClass> findAllBySearchTextAndWeek(String searchText, LocalDate startDate,LocalDate endDate);

}
