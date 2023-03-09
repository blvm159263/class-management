package com.mockproject.controller;

import com.mockproject.service.interfaces.IAttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/attendee")
public class AttendeeController {

    private final IAttendeeService service;

    @Operation(summary = "Get All User have Status = True")
    @GetMapping("")
    public ResponseEntity<?> listAllTrue(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
