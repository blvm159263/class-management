package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/class-schedule")
@Slf4j
public class ClassScheduleController {

    private final IClassScheduleService classScheduleService;

    @PostMapping("/day")
    @Operation(summary = "Get training class for the typical day")
    public ResponseEntity getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        var trainingClass = classScheduleService.getTrainingClassByDay(filterRequestDTO);
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/week")
    @Operation(summary = "Get a class schedule for a week")
    public ResponseEntity getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {

        var trainingClass = classScheduleService.getTrainingClassByWeek(filterRequestDTO);
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/search/day")
    @Operation(summary = "Search training class in day by text")
    public ResponseEntity searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO) {
        var trainingClass = classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(), searchByDTO.getNowDate());
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/search/week")
    @Operation(summary = "Search training class in week by text")
    public ResponseEntity searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO) {
        var trainingClass = classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(), searchByDTO.getStartDate(), searchByDTO.getEndDate());
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

}
