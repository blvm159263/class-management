package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get all Training Program")
    @GetMapping("list")
    public ResponseEntity<?> getAllTrainingProgram(){
        List<TrainingProgramDTO> list =service.getAllTrainingProgram();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get Training Program by program name and creator name")
    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search") @RequestParam(defaultValue = "") String name) {
        List<TrainingProgramDTO> list = service.searchByProgramName(name);
        for (TrainingProgramDTO dto:service.searchByCreatorName(name)){
            list.add(dto);
        }
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }
}
