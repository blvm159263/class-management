package com.mockproject.controller;

import com.mockproject.service.interfaces.IUnitService;
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
@RequestMapping("api/unit")
public class UnitController {

    private final IUnitService service;

    @Operation(summary = "Get All Unit by given Session ID")
    @GetMapping("list-by-session/{sid}")
    public ResponseEntity<?> listBySessionId(@Parameter(description = "Session ID") @PathVariable("sid") long sid){
        return ResponseEntity.ok(service.listBySessionId(sid));
    }
}
