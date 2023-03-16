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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
@Tag(name = "User API")
public class UserController {

    private final IUserService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get list admin successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get all User have role CLASS_ADMIN")
    @GetMapping("/class-admin")
    public ResponseEntity<?> listClassAdmin() {
        List<UserDTO> list = service.listClassAdminTrue();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User (Class Admin)!");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get list trainer successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get all User have role TRAINER")
    @GetMapping("/trainer")
    public ResponseEntity<?> listTrainer() {
        List<UserDTO> list = service.listTrainerTrue();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User (Trainer)!");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any User"),
            @ApiResponse(responseCode = "200", description = "When get user successfully!",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "Get User by given {ID}")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@Parameter(description = "User's ID") @PathVariable("id") Long id) {
        UserDTO user = service.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any User have ID = " + id);
        }
    }


    @GetMapping("/list")
    @Operation(
            summary = "Get user list"
    )
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(service.getAllUser(true));
    }



    @Operation(summary = "Get all class's Trainers by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/all-class-trainers")
    public ResponseEntity<?> getAllTrainers(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(service.getAllTrainersByTrainingClassId(id));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(summary = "Get class's Admins by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/all-class-admins")
    public ResponseEntity<?> getAllAdmins(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(service.getAllAdminsByTrainingClassId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(summary = "Get class's Creator by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/class-creator")
    public ResponseEntity<?> getCreator(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try {
            return ResponseEntity.ok(service.getCreatorByTrainingClassId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }



    @Operation(
            summary = "Get all class's Trainers for day-nth of total days of the class schedule",
            description = "Get list of Trainers in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Day [-] of Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/class-trainers-for-a-date")
    public ResponseEntity<?> getAllTrainersForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(service.getAllTrainersForADateByTrainingClassId(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }
}
