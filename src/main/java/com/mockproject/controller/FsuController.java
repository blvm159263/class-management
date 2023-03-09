package com.mockproject.controller;

import com.mockproject.service.interfaces.IFsuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/fsu")
public class FsuController {

    private final IFsuService service;

    @Operation(summary = "Get all FSU have status = True")
    @GetMapping("")
    public ResponseEntity<?> listAll(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
