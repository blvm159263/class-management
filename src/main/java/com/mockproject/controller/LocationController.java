package com.mockproject.controller;

import com.mockproject.dto.LocationDTO;
import com.mockproject.service.interfaces.ILocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/location")
public class LocationController {

    private final ILocationService service;

    @ApiResponse(responseCode = "404", description = "When don't find any Location")
    @Operation(summary = "Get all Location have status true")
    @GetMapping("")
    public ResponseEntity<?> listAllTrue(){
        List<LocationDTO> list = service.listAllTrue();
        if(!list.isEmpty()){
            return ResponseEntity.ok(list);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Location!");
        }
    }
}
