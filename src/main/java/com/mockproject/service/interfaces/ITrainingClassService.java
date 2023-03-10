package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITrainingClassService {

    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        String fsu, long trainerId, String search, String[] sort, Optional<Integer> page);
}
