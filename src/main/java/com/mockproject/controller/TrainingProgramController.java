package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/training-program")
public class TrainingProgramController {

    private final ITrainingProgramService service;

    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@RequestParam(defaultValue = "") String name){
        return ResponseEntity.ok(service.searchByName(name));
    }
}
