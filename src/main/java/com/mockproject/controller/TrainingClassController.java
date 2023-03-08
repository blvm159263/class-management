package com.mockproject.controller;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/class")
public class TrainingClassController {

    private final ITrainingClassService trainingClassService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id") int id){
        TrainingClassDTO result = trainingClassService.getAllDetails(id);
        return ResponseEntity.ok(result);
    }

}
