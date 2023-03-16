package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> origin/g3_trithuc_branch

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classschedule")
@Slf4j
public class ClassScheduleController {

    IClassScheduleService classScheduleService;

    @PostMapping("/day")
<<<<<<< HEAD
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        return classScheduleService.getTrainingClassByDay(filterRequestDTO);
    }

    @PostMapping("/week")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        log.info(filterRequestDTO.toString());
        return classScheduleService.getTrainingClassByWeek(filterRequestDTO);
=======
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
>>>>>>> origin/g3_trithuc_branch
    }

    @PostMapping("/search/day")
<<<<<<< HEAD
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(), searchByDTO.getNowDate());
=======
    public ResponseEntity searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO){
        var trainingClass= classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(),searchByDTO.getNowDate());
        if(trainingClass.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
        }else{
            return ResponseEntity.ok(trainingClass);
        }
>>>>>>> origin/g3_trithuc_branch
    }

    @PostMapping("/search/week")
<<<<<<< HEAD
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(), searchByDTO.getStartDate(), searchByDTO.getEndDate());
=======
    public ResponseEntity searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO){
        var trainingClass= classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(),searchByDTO.getStartDate(),searchByDTO.getEndDate());
        if(trainingClass.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Don't have any training class");
        }else{
            return ResponseEntity.ok(trainingClass);
        }
>>>>>>> origin/g3_trithuc_branch
    }

}
