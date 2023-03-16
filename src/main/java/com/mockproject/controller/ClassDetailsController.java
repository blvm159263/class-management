package com.mockproject.controller;


import com.mockproject.dto.*;
import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Class Details API")
@RequiredArgsConstructor
@RequestMapping("api/class-details")
public class ClassDetailsController {

    private final ITrainingClassService trainingClassService;

    @Operation(summary = "Get all fields from TrainingClass entity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = TrainingClassDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@Parameter(description = "TrainingClass id", example = "1") @PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(trainingClassService.getAllDetails(id));
        }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }

    }

    @Operation(summary = "Get all class's Trainers by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/trainers")
    public ResponseEntity<?> getAllTrainers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllTrainers(id));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class's Admins by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllAdmins(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class's Creator by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/creator")
    public ResponseEntity<?> getCreator(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try {
            return ResponseEntity.ok(trainingClassService.getCreator(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get all class's Locations(Towers) by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @GetMapping("/towers")
    public ResponseEntity<?> getAllTowers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllTowers(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class's Attendee type (Fresher,...) by TrainingClass id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = AttendeeDTO.class)))
    })
    @GetMapping("/attendee")
    public ResponseEntity<?> getAttendeeName(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getAttendee(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class schedule by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = ClassScheduleDTO.class)))
    })
    @GetMapping("/schedule")
    public ResponseEntity<?> getClassSchedule(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getClassSchedule(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class's Fsu by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = FsuDTO.class)))
    })
    @GetMapping("/fsu")
    public ResponseEntity<?> getClassFsu(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getFsu(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get class's Contact by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = ContactDTO.class)))
    })
    @GetMapping("/contact")
    public ResponseEntity<?> getClassContact(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getContact(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }

    @Operation(summary = "Get all class's DeliveryTypes ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = DeliveryTypeDTO.class)))
    })
    @GetMapping("/deliveryTypes")
    public ResponseEntity<?> getAllDeliveryTypes(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllDeliveryTypes(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }


    @Operation(
            summary = "Get all Units for day-nth of total days of the class schedule",
            description = "Get list of Units in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = UnitDTO.class)))
    })
    @GetMapping("/units-for-a-date")
    public ResponseEntity<?> getAllUnitsForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllUnitsForADate(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(
            summary = "Get all Trainers for day-nth of total days of the class schedule",
            description = "Get list of Trainers in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/trainers-for-a-date")
    public ResponseEntity<?> getAllTrainersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllTrainersForADate(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(
            summary = "Get all Locations(Towers) for day-nth of total days of the class schedule",
            description = "Get list of Locations(Towers) in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value"),
            @ApiResponse(responseCode = "200", description = "Return Sample",
                    content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @GetMapping("/towers-for-a-date")
    public ResponseEntity<?> getAllTowersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(trainingClassService.getAllTowersForADate(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }

}
