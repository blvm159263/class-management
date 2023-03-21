package com.mockproject.controller;

import com.mockproject.dto.TowerDTO;
import com.mockproject.service.interfaces.ITowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Tower API")
@RequestMapping("api/tower")
@SecurityRequirement(name = "Authorization")
public class TowerController {

    private final ITowerService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Tower"),
            @ApiResponse(responseCode = "200", description = "When found Tower",
            content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @Operation(summary = "Get all Tower have status true by given Location Id")
    @GetMapping("location/{id}")
    public ResponseEntity<?> listByLocationIdTrue(@Parameter(description = "Location ID want to get Tower") @PathVariable("id") Long id) {
        List<TowerDTO> list = service.listByTowerIdTrue(id);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Tower with Location ID = " + id);
        }
    }


    //Get the tower that the training class will study on that day
    @Operation(
            summary = "Get all the tower that the training class studies by Training Class ID",
            description = "<b>List all the tower that the training class will study<b>"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "No Such Value",
                    content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @GetMapping("trainingClass/{id}")
    public ResponseEntity<?> getTowerByClassId(
            @PathVariable ("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get all the tower</b>",
                    example = "1"
            )
            Long id) {
        try {
            return ResponseEntity.ok(service.getAllTowersByTrainingClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any tower with training class Id is " + id);
        }
    }



    //Get the tower that training class will study on that day
    @Operation(
            summary = "Get the tower that the training class studies on that day",
            description = "<b>List the information of the tower that the training class will study<b>"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "No Such Value",
                    content = @Content(schema = @Schema(defaultValue = "Day [-] of Training class id[-] not found!!!"))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @GetMapping("trainingClass/tower-on-date")
    public ResponseEntity<?> getTowerOnTheDay(
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            Long id,
            @Parameter(
                    description = "<b>Insert ID of the day</b>",
                    example = "1"
            )
            int dayNth) {
        try {
            return ResponseEntity.ok(service.getTowerForTheDayByTrainingClassId(id, dayNth));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any tower with training class Id is " + id
                            +" and day with Id is " + dayNth);
        }
    }
}
