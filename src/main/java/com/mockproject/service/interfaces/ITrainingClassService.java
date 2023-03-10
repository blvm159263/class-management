package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.entity.TrainingClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public interface ITrainingClassService {
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date);
    public List<TrainingClass> findAllBySpecification(Specification specification);

}
