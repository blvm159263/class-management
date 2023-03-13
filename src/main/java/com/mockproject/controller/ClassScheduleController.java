package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/classschedule")
@Slf4j
public class ClassScheduleController {
    @Autowired
    IClassScheduleService classScheduleService;

    @GetMapping("/day")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO){
        return classScheduleService.getTrainingClassByDay(filterRequestDTO);
    }

    @GetMapping("/week")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO){
        log.info(filterRequestDTO.toString());
        return classScheduleService.getTrainingClassByWeek(filterRequestDTO);
    }
    @GetMapping("/search/day")
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO){
        return classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(),searchByDTO.getNowDate());
    }
    @GetMapping("/search/week")
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO){
        return classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(),searchByDTO.getStartDate(),searchByDTO.getEndDate());
    }

}
