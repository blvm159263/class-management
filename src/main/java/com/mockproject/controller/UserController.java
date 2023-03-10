package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final IUserService service;

    @ApiResponse(responseCode = "404", description = "When don't find any User")
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

    @ApiResponse(responseCode = "404", description = "When don't find any User")
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

    @ApiResponse(responseCode = "404", description = "When don't find any User")
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
}
