package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID"
    )
    public ResponseEntity<?> getUserById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert ID to get user<b>",
                    example = "1"
            ) long id){
        return ResponseEntity.ok(userService.getUserById(true, id));
    }

    @GetMapping("/list")
    @Operation(
            summary = "Get user list"
    )
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser(true));
    }
}
