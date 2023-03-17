package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.IUserService;
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
@Tag(name = "User API")
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;

    //Get the trainer by training class ID
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get the information of the trainer of the training class by the training class ID",
            description = "<b>List the information of the trainer of the training class</b>"
    )
    @GetMapping("trainingClass/trainer/{id}")
    public ResponseEntity<?> getTrainerByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(userService.getTrainerByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find trainer with training class id " + id);
        }
    }

    //Get the trainer by id and day
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get the information of the trainer that the training class will study on that day",
            description = "<b>List the information of the trainer of the training class on that day</b>"
    )
    @GetMapping("trainingClass/trainerOnDay")
    public ResponseEntity<?> getTrainerOnTheDay(
            @Parameter(
                    description = "<b>Insert the training class ID</b>",
                    example = "1"
            ) long id,
            @Parameter(
                    description = "<b>Insert ID of the day</b>",
                    example = "1"
            ) int day) {
        try {
            return ResponseEntity.ok(userService.getTrainerOnThisDayById(id, day));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any trainer with training class Id " + id +
                            " and with day Id is " + day);
        }
    }

    //Get the creator by training class ID
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get the information of the creator of the training class by the training class ID",
            description = "<b>List the information of the creator of the training class</b>"
    )
    @GetMapping("trainingClass/creator/{id}")
    public ResponseEntity<?> getCreatorByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(userService.getCreatorByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any creator with training class Id " + id);
        }
    }

    //Get the reviewer by training class ID
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get the information of the reviewer of the training class by the training class ID",
            description = "<b>List the information of the reviewer of the training class</b>"
    )
    @GetMapping("trainingClass/reviewer/{id}")
    public ResponseEntity<?> getReviewerByClassCode(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(userService.getReviewerByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any reviewer with training class Id " + id);
        }
    }

    //Get the approver by training class ID
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get the information of the approver of the training class by the training class ID",
            description = "<b>List the information of the approver of the training class</b>"
    )
    @GetMapping("trainingClass/approver/{id}")
    public ResponseEntity<?> getApproverByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(userService.getApproverByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any approver with training class Id " + id);
        }

    }
}
