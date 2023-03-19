package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.UnitDetail;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IOutputStandardService;
import com.mockproject.service.interfaces.ISyllabusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;
import org.webjars.NotFoundException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepo;

    private final OutputStandardRepository outputStandardRepo;

    private final UnitDetailRepository unitDetailRepo;

    private static final int RESULTS_PER_PAGE = 10;

    @Override
    public Page<SyllabusDTO> getListSyllabus(boolean status, LocalDate fromDate, LocalDate toDate,
                                             List<String> search, String[] sort, Optional<Integer> page){
        List<Sort.Order> order = new ArrayList<>();
        int skipCount = page.orElse(0) * RESULTS_PER_PAGE;
        Set<String> sourceFieldList = getAllFields(new Syllabus().getClass());
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                if(ifPropertpresent(sourceFieldList, subSort[0])){
                    order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
                } else {
                    throw new NotFoundException(subSort[0] + " is not a propertied of Syllabus!");
                }
            }
        } else {
            if(sort.length == 1){
                throw new ArrayIndexOutOfBoundsException("Sort direction(asc/desc) not found!");
            }
            if(ifPropertpresent(sourceFieldList, sort[0])){
                order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
            } else {
                throw new NotFoundException(sort[0] + " is not a propertied of Syllabus!");
            }
        }
        List<Syllabus> pages = syllabusRepo.getListSyllabus(status, fromDate, toDate, search.size() > 0 ? search.get(0) : "", getListSyllabusIdByOSD(search.size() > 0 ? search.get(0) : ""), Sort.by(order));
        if (search.size() > 1){
            for (int i = 1; i < search.size(); i++) {
                String subSearch = search.get(i).toUpperCase();
                pages = pages.stream().filter(s
                        -> s.getName().toUpperCase().contains(subSearch) ||
                                s.getCode().toUpperCase().contains(subSearch) ||
                                s.getCreator().getFullName().toUpperCase().contains(subSearch) ||
                                checkOsdBelongSyllabus(s.getId(), subSearch))
                        .collect(Collectors.toList());
            }
        }
        if(pages.size() > 0){
            return new PageImpl<>(
                    pages.stream().skip(skipCount).limit(RESULTS_PER_PAGE).map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    PageRequest.of(page.orElse(0), RESULTS_PER_PAGE, Sort.by(order)),
                    pages.size());
        } else {
            throw new NotFoundException("Syllabus not found!");
        }
    }

    private boolean checkOsdBelongSyllabus(long syllabusId, String search) {
            if (getListSyllabusIdByOSD(search).contains(syllabusId)) {
                return true;
            }
        return false;
    }

    private static boolean ifPropertpresent(final Set<String> properties, final String propertyName) {
        if (properties.contains(propertyName)) {
            return true;
        }
        return false;
    }

    private static Set<String> getAllFields(final Class<?> type) {
        Set<String> fields = new HashSet<>();
        //loop the fields using Java Reflections
        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        //recursive call to getAllFields
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
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
