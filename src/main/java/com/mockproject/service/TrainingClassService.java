package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository classRepo;

    private final TrainingClassUnitInformationRepository classUnitRepo;

    @Override
    public Page<TrainingClassDTO> getListClass(boolean status,
                                               List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                               List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                               String fsu, long trainerId, String search, String[] sort, Optional<Integer> page)
    {
        List<Sort.Order> order = new ArrayList<>();
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else{
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by(order));
        Page<TrainingClass> pages = classRepo.getListClass(status, locationId, fromDate, toDate, period,
                isOnline, state, attendeeId, fsu, classUnitRepo
                        .findByStatusAndAndTrainerId( true, trainerId)
                        .stream()
                        .map(t -> t.getTrainingClass().getId())
                        .collect(Collectors.toList()), search, pageable);
        return new PageImpl<>(
                pages.stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                pages.getPageable(),
                pages.getTotalElements());
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
