package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import com.mockproject.service.TrainingProgramService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/view")
public class TrainingProgramController {
    @Autowired
    private final TrainingProgramService trainingProgramService;

    @GetMapping("/search")
    public ResponseEntity<List<TrainingProgram>> searchProgramP(@RequestParam("query") String query){
        return ResponseEntity.ok(trainingProgramService.searchProgramP(query));
    }

}