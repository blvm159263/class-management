package com.mockproject.controller;

import com.mockproject.service.interfaces.IAttendeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/attendee")
public class AttendeeController {

    private final IAttendeeService service;

    @GetMapping("")
    public ResponseEntity<?> listAllTrue(){
        return ResponseEntity.ok(service.listAllTrue());
    }
}
