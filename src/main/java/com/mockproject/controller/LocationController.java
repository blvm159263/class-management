package com.mockproject.controller;

import com.mockproject.service.interfaces.ILocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Location API")
@RequestMapping("/api/location")
public class LocationController {

    private final ILocationService locationService;

    @GetMapping("/list")
    @Operation(
            summary = "Get location list"
    )
    public ResponseEntity<?> getAllLocation(){
        return ResponseEntity.ok(locationService.getAllLocation(true));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get location by ID"
    )
    public ResponseEntity<?> getLocationById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert ID to get location<b>",
                    example = "1"
            ) long id) {
        return ResponseEntity.ok(locationService.getLocationById(true, id));
    }
}
