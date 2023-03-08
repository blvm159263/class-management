package com.mockproject.controller;

import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainingclass")
@Slf4j
public class ClassScheduleController {
    @Autowired
    IClassScheduleService classScheduleService;

    @GetMapping("/day/{day}")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@PathVariable("day")LocalDate date){
        return classScheduleService.getTrainingClassByDay(date);
    }

    @PostMapping("/week")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO){
        log.info(filterRequestDTO.toString());
        return classScheduleService.getTrainingClassByWeek(filterRequestDTO);
    }
}
