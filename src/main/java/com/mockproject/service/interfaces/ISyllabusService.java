package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISyllabusService {

    Page<SyllabusDTO> getListSyllabus(boolean status,
                                      LocalDate fromDate, LocalDate toDate,
                                      List<String> search, String[] sort, Optional<Integer> page);

    List<Long> getListSyllabusIdByOSD(String osd);
}
