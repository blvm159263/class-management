package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/classschedule")
@Slf4j
public class ClassScheduleController {
    @Autowired
    IClassScheduleService classScheduleService;

    @PostMapping("/day")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        return classScheduleService.getTrainingClassByDay(filterRequestDTO);
    }

    @PostMapping("/week")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        log.info(filterRequestDTO.toString());
        return classScheduleService.getTrainingClassByWeek(filterRequestDTO);
    }

    @PostMapping("/search/day")
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(), searchByDTO.getNowDate());
    }

    @PostMapping("/search/week")
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(), searchByDTO.getStartDate(), searchByDTO.getEndDate());
    }

}
