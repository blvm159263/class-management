package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-program")
public class TrainingProgramController {

    private final ITrainingProgramService service;

    @Operation(summary = "Get Training Program by searching name")
    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search") @RequestParam(defaultValue = "") String name){
        return ResponseEntity.ok(service.searchByName(name));
    }
}
