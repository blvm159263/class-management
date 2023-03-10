package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-program")
public class TrainingProgramController {

    private final ITrainingProgramService service;
    @ApiResponse(responseCode = "404", description = "When don't find any Training Program")
    @Operation(summary = "Get Training Program by searching name")
    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search") @RequestParam(defaultValue = "") String name){
        List<TrainingProgramDTO> list = service.searchByName(name);
        if(!list.isEmpty()){
            return ResponseEntity.ok(list);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }
}
