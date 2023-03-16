package com.mockproject.service.interfaces;

import org.springframework.data.domain.Page;
import com.mockproject.dto.*;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface ITrainingClassService {
    TrainingClassDTO getAllDetails(long id); // done


    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        long fsu, long trainerId, String search, String[] sort, Optional<Integer> page);

    List<TrainingClassDTO> getAllClass();

    Long create(TrainingClassDTO trainingClassDTO);

}
