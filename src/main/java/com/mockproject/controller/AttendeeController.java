package com.mockproject.controller;

import com.mockproject.service.interfaces.IAttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Attendee API")
@RequestMapping("/api/attendee")
public class AttendeeController {

    private final IAttendeeService attendeeService;

    @GetMapping("/list")
    @Operation(
            summary = "Get attendee list"
    )
    public ResponseEntity<?> getAllAttendee(){
        return ResponseEntity.ok(attendeeService.getAllAttendee(true));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get attendee by ID"
    )
    public ResponseEntity<?> getAttendeeById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert ID to get attendee<b>",
                    example = "1"
            ) long id) {
        return ResponseEntity.ok(attendeeService.getAttendeeById(true, id));
    }
}
