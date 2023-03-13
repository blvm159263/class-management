package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-class-admin")
public class TrainingClassAdminController {

    private final ITrainingClassAdminService service;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When list saved successful!"),
            @ApiResponse(responseCode = "400", description = "When saving fail!")
    })
    @Operation(summary = "Save list of class admin by given Training Class Id")
    @PostMapping("training-class/{tcId}")
    public ResponseEntity<?> createListClassAdmin(@Parameter(description = "List ID of class admin")
                                                      @RequestBody List<Long> listAdminId,
                                                  @Parameter(description = "Training Class ID when call create Training Class API return")
                                                      @PathVariable("tcId") Long tcId){
        if(service.saveList(listAdminId, tcId)){
            return new ResponseEntity<>("List Class Admin have been saved successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Saving fail!", HttpStatus.BAD_REQUEST);
    }
}
