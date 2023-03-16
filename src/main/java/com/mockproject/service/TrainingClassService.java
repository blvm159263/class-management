package com.mockproject.service;

import com.mockproject.entity.TrainingClass;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository repository;

    @Override
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date) {
        return repository.findAllByListClassSchedulesDate(date);
    }

    @Override
    public List<TrainingClass> findAllBySpecification(Specification specification) {
        return repository.findAll(specification);
    }

    @Override
    public List<TrainingClass> findAllBySearchTextAndDate(String searchText, LocalDate date) {
        return repository.findAllBySearchTextAndListClassSchedulesDate(searchText,date);
    }

    @Override
    public List<TrainingClass> findAllBySearchTextAndWeek(String searchText, LocalDate startDate, LocalDate endDate) {
        return repository.findAllBySearchTextAndListClassSchedulesWeek(searchText,startDate,endDate);
    }

}
