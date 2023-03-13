package com.mockproject.service.interfaces;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.entity.TrainingClass;

import java.time.LocalDate;
import java.util.List;

public interface IClassScheduleService {

    List<ClassScheduleDTO> listAll();

    List<ClassSchedule> listEntity();

    ClassSchedule save(ClassScheduleDTO dto);

    Long countDayBefore(LocalDate date, Long id);

    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(LocalDate date);

    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(TrainingClassFilterRequestDTO filterRequestDTO);

    public TrainingClassFilterResponseDTO getTrainingClassDetail(TrainingClass trainingClass,LocalDate date);


    List<TrainingClass> searchTrainingClassByWeek(String search);
}
