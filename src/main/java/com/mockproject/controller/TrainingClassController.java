package com.mockproject.controller;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> create(@RequestBody TrainingClassDTO dto){
        Long id = service.create(dto);
        if(id!=null){
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Can't not create Training Class", HttpStatus.BAD_REQUEST);
        }
    }
}
