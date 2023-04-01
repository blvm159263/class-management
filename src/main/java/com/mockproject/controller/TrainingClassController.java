package com.mockproject.controller;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-class")
public class TrainingClassController {

    private final ITrainingClassService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When Training Class created successfully!"),
            @ApiResponse(responseCode = "400", description = "When Training Class can't be created - Object is not valid!")
    })
    @Operation(summary = "Create new Training Class")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TrainingClassDTO dto) {
        Long id = service.create(dto);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Can't not create Training Class", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When Training Class created successfully!"),
            @ApiResponse(responseCode = "400", description = "When Training Class can't be created - Object is not valid!")
    })
    @Operation(summary = "Duplicate new Training Class")
    @PostMapping("/duplicate/{id}")
    public ResponseEntity<?> duplicateClass(@PathVariable("id") Long id) {
        boolean status = service.duplicateClass(id);
        if (status) {
            return new ResponseEntity<>("Created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Can't not duplicate Training Class", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "When Training Class deleted successfully!"),
            @ApiResponse(responseCode = "404", description = "When Training Class can't be found!")
    })
    @Operation(summary = "Delete Training Class")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteTrainingClass(id));
    }
}




