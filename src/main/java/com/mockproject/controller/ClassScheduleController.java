package com.mockproject.controller;

import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/class-schedule")
public class ClassScheduleController {

    private final IClassScheduleService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When list of ClassShedule have been saved success!"),
            @ApiResponse(responseCode = "400", description = "When Saving fail!")
    })
    @Operation(summary = "Save list of ClassSchedule by given Training Class")
    @PostMapping("list/traing-class/{tcId}")
    public ResponseEntity<?> createListSchedule(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                content = @Content(examples = @ExampleObject(value = "[\"2023-03-14\",\"2023-03-15\"]")))
                                                @Valid @RequestBody List<LocalDate> listDate,
                                                @Parameter(description = "Training Class ID when call create Training Class API return")
                                                @PathVariable("tcId") Long tcId){
        if(service.saveClassScheduleForTrainingClass(listDate, tcId)){
            return new ResponseEntity<>("List of Date have been saved!", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Saving Fail!", HttpStatus.BAD_REQUEST);
        }
    }
}
