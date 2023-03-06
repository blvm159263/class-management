package com.mockproject.controller;

import com.mockproject.service.interfaces.IFsuService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/fsu")
public class FsuController {

    private final IFsuService service;

    public ResponseEntity<?> listAll(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
