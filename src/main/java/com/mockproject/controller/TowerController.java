package com.mockproject.controller;

import com.mockproject.service.interfaces.ITowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tower")
public class TowerController {

    private final ITowerService service;

    @Operation(summary = "Get all Tower have status true by given Location Id")
    @GetMapping("location/{id}")
    public ResponseEntity<?> listByLocationIdTrue(@Parameter(description = "Location ID want to get Tower") @PathVariable("id")long id){
        return ResponseEntity.ok(service.listByTowerIdTrue(id));
    }
}
