package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISyllabusService {

    Page<SyllabusDTO> getListSyllabus(boolean status,
                                      LocalDate fromDate, LocalDate toDate,
                                      String search, String[] sort, Optional<Integer> page);

    Sort.Direction getSortDirection(String direction);

    List<Long> getListSyllabusIdByOSD(String osd);
}
