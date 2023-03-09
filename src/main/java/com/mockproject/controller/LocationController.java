package com.mockproject.controller;

import com.mockproject.service.interfaces.ILocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/location")
public class LocationController {

    private final ILocationService service;

    @Operation(summary = "Get all Location have status true")
    @GetMapping("")
    public ResponseEntity<?> listAllTrue(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
