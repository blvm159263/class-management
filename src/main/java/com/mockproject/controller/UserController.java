package com.mockproject.controller;

import com.mockproject.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final IUserService service;

    @Operation(summary = "Get all User have role CLASS_ADMIN")
    @GetMapping("/class-admin")
    public ResponseEntity<?> listClassAdmin(){
        return ResponseEntity.ok(service.listClassAdminTrue());
    }

    @Operation(summary = "Get all User have role TRAINER")
    @GetMapping("/trainer")
    public ResponseEntity<?> listTrainer(){
        return ResponseEntity.ok(service.listTrainerTrue());
    }

    @Operation(summary = "Get User by given {ID}")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@Parameter(description = "User's ID") @PathVariable("id") long id){
        return ResponseEntity.ok(service.getUserById(id));
    }
}
