package com.mockproject.service.interfaces;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;

import java.util.List;

public interface IClassScheduleService {

    List<ClassScheduleDTO> listAll();

    List<ClassSchedule> listEntity();

    ClassSchedule save(ClassScheduleDTO dto);

    List<ClassScheduleDTO> getScheduleByClassCode(String code);
}
