package com.mockproject.controller;

import com.mockproject.service.interfaces.IUnitDetailService;
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
@RequestMapping("api/unit-detail")
public class UnitDetailController {

    private final IUnitDetailService service;

    @Operation(summary = "Get all Unit Detail of Unit by given Unit ID")
    @GetMapping("list-by-unit/{uid}")
    public ResponseEntity<?> listByUnitId(@Parameter(description = "Unit ID want to get detail") @PathVariable("uid") long uid){
        return ResponseEntity.ok(service.listByUnitIdTrue(uid));
    }
}
