package com.mockproject.controller;

import com.mockproject.service.interfaces.ISessionService;
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
@RequestMapping("api/session")
public class SessionController {

    private final ISessionService service;

    @Operation(summary = "Get all Session by given syllabus ID")
    @GetMapping("list-by-syllus/{sid}")
    public ResponseEntity<?> listBySyllabusId(@Parameter(description = "Syllabus's ID") @PathVariable("sid") long sid){
        return ResponseEntity.ok(service.listBySyllabus(sid));
    }
}
