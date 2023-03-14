package com.mockproject.service.interfaces;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;

import java.time.LocalDate;
import java.util.List;

public interface IClassScheduleService {

    List<ClassScheduleDTO> listAll();

    boolean saveClassScheduleForTrainingClass(List<LocalDate> listDate, Long tcId);
}
