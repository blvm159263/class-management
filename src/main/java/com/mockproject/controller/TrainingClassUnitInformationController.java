package com.mockproject.controller;

import com.mockproject.dto.TrainingClassUnitInformationDTO;
import com.mockproject.service.interfaces.ITrainingClassUnitInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.PatternProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-class-unit-information")
public class TrainingClassUnitInformationController {

    private final ITrainingClassUnitInformationService service;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",description = "When list of Information saved successfully"),
        @ApiResponse(responseCode = "400", description = "When saving Fail!")
    })
    @Operation(summary = "Save list of Unit Information when creating Class")

    @PostMapping("list")
    public ResponseEntity<?> createListOfInformation( @RequestBody List<TrainingClassUnitInformationDTO> listDto){
        if(service.saveList(listDto)){
            return new ResponseEntity<>("List of Unit Information have been save!", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Save Fail!", HttpStatus.BAD_REQUEST);
        }
    }
}
