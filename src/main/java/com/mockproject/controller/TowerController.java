package com.mockproject.controller;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.TowerDTO;
import com.mockproject.service.interfaces.ITowerService;
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
@Tag(name = "Tower API")
@RequiredArgsConstructor
@RequestMapping("api/tower")
public class TowerController {

    private final ITowerService towerService;

    //Get the tower that the training class will study on that day
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @Operation(
            summary = "Get all the tower that the training class studies by Training Class ID",
            description = "<b>List all the tower that the training class will study<b>"
    )
    @GetMapping("trainingClass/{id}")
    public ResponseEntity<?> getTowerByClassId(
            @PathVariable ("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get all the tower</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(towerService.getTowerByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any tower with training class Id is " + id);
        }
    }

    //Get all the tower
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @Operation(
            summary = "Get the tower that the training class studies on that day",
            description = "<b>List the information of the tower that the training class will study<b>"
    )
    @GetMapping("trainingClass/tower-on-date")
    public ResponseEntity<?> getTowerOnTheDay(
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id,
            @Parameter(
                    description = "<b>Insert ID of the day</b>",
                    example = "1"
            )
            int day) {
        try {
            return ResponseEntity.ok(towerService.getTowerForTheDay(id, day));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any tower with training class Id is " + id
                    +" and day with Id is " + day);
        }

    }
}
