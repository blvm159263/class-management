package com.mockproject.controller;

import com.mockproject.service.interfaces.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/contact")
public class ContactController {

    private final IContactService service;

    @Operation(summary = "Get all Contact have status = True")
    @GetMapping("")
    public ResponseEntity<?> listAll(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
