package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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

    private static final int RESULTS_PER_PAGE = 10;

    @Override
    public Page<TrainingClassDTO> getListClass(boolean status,
                                               List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                               List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                               long fsu, long trainerId, List<String> search, String[] sort, Optional<Integer> page)
    {
        List<Sort.Order> order = new ArrayList<>();
        int skipCount = page.orElse(0) * RESULTS_PER_PAGE;
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else{
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        List<Long> classId = new ArrayList<>();
        if(trainerId!=0){
            classId = classUnitRepo
                    .findByStatusAndTrainerId( true, trainerId)
                    .stream().map(t -> t.getTrainingClass().getId())
                    .collect(Collectors.toList());
            classId.add(-1L);
        }
        List<TrainingClass> pages = classRepo.getListClass(status, locationId, fromDate, toDate, period,
                isOnline, state, attendeeId, fsu, classId, search.size() > 0 ? search.get(0) : "", Sort.by(order));
        if (search.size() > 1){
            for (int i = 1; i < search.size(); i++) {
                String subSearch = search.get(i).toUpperCase();
                pages = pages.stream().filter(c
                                -> c.getClassName().toUpperCase().contains(subSearch) ||
                                c.getClassCode().toUpperCase().contains(subSearch) ||
                                c.getCreator().getFullName().toUpperCase().contains(subSearch))
                        .collect(Collectors.toList());
            }
        }
        if(pages.size() > 0){
            return new PageImpl<>(
                    pages.stream().skip(skipCount).limit(RESULTS_PER_PAGE).map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    PageRequest.of(page.orElse(0), 10, Sort.by(order)),
                    pages.size());
        }else {
            throw new NotFoundException("Training Class not found!");
        }
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public List<TrainingClassDTO> getAllClass() {
        return classRepo.findAllByStatus(true).stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
