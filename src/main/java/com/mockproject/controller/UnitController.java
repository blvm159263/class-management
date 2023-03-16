package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.service.UnitService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Unit API")
@RequiredArgsConstructor
@RequestMapping("api/unit")
public class UnitController {
    private final UnitService unitService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UnitDTO.class)))
    })
    @Operation(
            summary = "Get the unit that the training class studies on that day",
            description = "<b>List the information of the unit that the training class studies on that day"
    )
    @GetMapping("trainingClassStudies")
    public ResponseEntity<?> getUnitFromTheDate(
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            ) long id,
            @Parameter(
                    description = "<b>Insert the Id of the day to get the unit of the Training Class</b>",
                    example = "1"

            ) int day) {
        try {
            return ResponseEntity.ok(unitService.getAllUnitsForTheDate(id, day));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any unit that the training class with Id is" + id +
                            " and with day Id is " + day + "that the training class will study");
        }
    }

}
