package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.UnitDetail;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepo;

    private final OutputStandardRepository outputStandardRepo;

    private final UnitDetailRepository unitDetailRepo;

    @Override
    public Page<SyllabusDTO> getListSyllabus(boolean status, LocalDate fromDate, LocalDate toDate,
                                             String search, String[] sort, Optional<Integer> page) {
        List<Sort.Order> order = new ArrayList<>();
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else {
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by(order));
        Page<Syllabus> pages = syllabusRepo.getListSyllabus(status, fromDate, toDate, search, getListSyllabusIdByOSD(search), pageable);
        if(pages.getContent().size() > 0){
            return new PageImpl<>(
                    pages.stream().map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    pages.getPageable(),
                    pages.getTotalElements());
        } else {
            throw new NotFoundException("Syllabus not found!");
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
    public List<Long> getListSyllabusIdByOSD(String osd) {
        List<UnitDetail> detailList = unitDetailRepo.findByStatusAndOutputStandardIn(true, outputStandardRepo.findByStatusAndStandardCodeContainingIgnoreCase(true, osd));
        return detailList.stream().map(ob
                -> ob.getUnit().getSession().getSyllabus().getId()).collect(Collectors.toList());
    }
}
