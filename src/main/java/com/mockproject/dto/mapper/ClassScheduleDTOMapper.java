package com.mockproject.dto.mapper;


import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class ClassScheduleDTOMapper implements Function<ClassSchedule, ClassScheduleDTO> {


    @Override
    public ClassScheduleDTO apply(ClassSchedule classSchedule) {
        return new ClassScheduleDTO(
                classSchedule.getId(),
                classSchedule.getDate(),
                classSchedule.isStatus(),
                classSchedule.getTrainingClass().getId()
        );
    }
}
