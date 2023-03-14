package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Class Details API")
@RequiredArgsConstructor
@RequestMapping("api/class")
public class TrainingClassController {

    private final ITrainingClassService trainingClassService;

    @Operation(summary = "Get all fields from TrainingClass entity by id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@Parameter(description = "TrainingClass id", example = "1") @PathVariable("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllDetails(id));
    }

    @Operation(summary = "Get all class's Trainers by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/trainers")
    public ResponseEntity<?> getAllTrainers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllTrainers(id));
    }

    @Operation(summary = "Get class's Admins by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllAdmins(id));
    }

    @Operation(summary = "Get class's Creator by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/creator")
    public ResponseEntity<?> getCreator(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getCreator(id));
    }

    @Operation(summary = "Get all class's Locations(Towers) by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/towers")
    public ResponseEntity<?> getAllTowers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllTowers(id));
    }

    @Operation(summary = "Get class's Attendee type (Fresher,...) by TrainingClass id ")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/attendee")
    public ResponseEntity<?> getAttendeeName(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAttendee(id));
    }

    @Operation(summary = "Get class schedule by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/schedule")
    public ResponseEntity<?> getClassSchedule(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getClassSchedule(id));
    }

    @Operation(summary = "Get class's Fsu by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/fsu")
    public ResponseEntity<?> getClassFsu(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getFsu(id));
    }

    @Operation(summary = "Get class's Contact by TrainingClass id")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/contact")
    public ResponseEntity<?> getClassContact(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getContact(id));
    }

    @Operation(summary = "Get all class's DeliveryTypes ")
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/deliveryTypes")
    public ResponseEntity<?> getAllDeliveryTypes(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllDeliveryTypes(id));
    }


    @Operation(
            summary = "Get all Units for day-nth of total days of the class schedule",
            description = "Get list of Units in a date clicked in the class schedule table by the user"
    )
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/units-for-a-date")
    public ResponseEntity<?> getAllUnitsForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        return ResponseEntity.ok(trainingClassService.getAllUnitsForADate(id, dayNth));
    }



    @Operation(
            summary = "Get all Trainers for day-nth of total days of the class schedule",
            description = "Get list of Trainers in a date clicked in the class schedule table by the user"
    )
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/trainers-for-a-date")
    public ResponseEntity<?> getAllTrainersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        return ResponseEntity.ok(trainingClassService.getAllTrainersForADate(id, dayNth));
    }



    @Operation(
            summary = "Get all Units for day-nth of total days of the class schedule",
            description = "Get list of Units in a date clicked in the class schedule table by the user"
    )
    @ApiResponse(responseCode = "500", description = "No Such Value")
    @GetMapping("/towers-for-a-date")
    public ResponseEntity<?> getAllTowersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        return ResponseEntity.ok(trainingClassService.getAllTowersForADate(id, dayNth));
    }
}
