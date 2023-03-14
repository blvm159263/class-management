package com.mockproject.controller;

import com.mockproject.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API")
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

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
