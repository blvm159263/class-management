package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/classschedule")
@Slf4j
public class ClassScheduleController {
    @Autowired
    IClassScheduleService classScheduleService;

    @PostMapping("/day")
    public ResponseEntity getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO){
       var trainingClass= classScheduleService.getTrainingClassByDay(filterRequestDTO);
       if(trainingClass.isEmpty()){
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
       }else{
           return ResponseEntity.ok(trainingClass);
       }
    }

    @PostMapping("/week")
    public ResponseEntity getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO){
        var trainingClass= classScheduleService.getTrainingClassByWeek(filterRequestDTO);
        if(trainingClass.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
        }else{
            return ResponseEntity.ok(trainingClass);
        }
    }
    @PostMapping("/search/day")
    public ResponseEntity searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO){
        var trainingClass= classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(),searchByDTO.getNowDate());
        if(trainingClass.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
        }else{
            return ResponseEntity.ok(trainingClass);
        }
    }
    @PostMapping("/search/week")
    public ResponseEntity searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO){
        var trainingClass= classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(),searchByDTO.getStartDate(),searchByDTO.getEndDate());
        if(trainingClass.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
        }else{
            return ResponseEntity.ok(trainingClass);
        }
    }

}
