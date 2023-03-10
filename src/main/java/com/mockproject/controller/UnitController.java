package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.service.interfaces.IUnitService;
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
@RequestMapping("api/unit")
public class UnitController {

    private final IUnitService service;

    @ApiResponse(responseCode = "404", description = "When don't find any Unit")
    @Operation(summary = "Get All Unit by given Session ID")
    @GetMapping("list-by-session/{sid}")
    public ResponseEntity<?> listBySessionId(@Parameter(description = "Session ID") @PathVariable("sid") Long sid){
        List<UnitDTO> list = service.listBySessionId(sid);
        if(!list.isEmpty()){
            return ResponseEntity.ok(list);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Unit!");
        }
    }
}
