package com.mockproject.controller;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.service.interfaces.IUnitDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/unit-detail")
public class UnitDetailController {

    private final IUnitDetailService service;

    @ApiResponse(responseCode = "404", description = "When don't find any Unit Detail")
    @Operation(summary = "Get all Unit Detail of Unit by given Unit ID")
    @GetMapping("list-by-unit/{uid}")
    public ResponseEntity<?> listByUnitId(@Parameter(description = "Unit ID want to get detail") @PathVariable("uid") Long uid) {
        List<UnitDetailDTO> list = service.listByUnitIdTrue(uid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Unit Detail with Unit ID = " + uid);
        }
    }
}
