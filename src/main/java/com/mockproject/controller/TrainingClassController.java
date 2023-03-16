package com.mockproject.controller;

import com.mockproject.dto.TowerDTO;
import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Training Class API")
@RequiredArgsConstructor
@RequestMapping("api/trainingClass")
public class TrainingClassController {
    private final ITrainingClassService trainingClassService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = TrainingClassDTO.class)))
    })
    @Operation(
            summary = "Get all the data of the training class"
    )
    @GetMapping("{id}")
    public ResponseEntity<?> getTrainingClassByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id){
        try {
            return  ResponseEntity.ok(trainingClassService.getTrainingClassByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any training class with Id " + id);
        }
    }
}
