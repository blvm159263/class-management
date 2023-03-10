package com.mockproject.controller;

import com.mockproject.dto.FsuDTO;
import com.mockproject.service.interfaces.IFsuService;
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
@RequestMapping("api/fsu")
public class FsuController {

    private final IFsuService service;

    @ApiResponse(responseCode = "404", description = "When don't find any Fsu")
    @Operation(summary = "Get all FSU have status = True")
    @GetMapping("")
    public ResponseEntity<?> listAll(){
        List<FsuDTO> list = service.listAllTrue();
        if(!list.isEmpty()){
            return ResponseEntity.ok(list);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Fsu!");
        }
    }
}
