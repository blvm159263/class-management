package com.mockproject.controller;


import com.mockproject.dto.AttendeeDTO;
import com.mockproject.service.interfaces.IAttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Attendee API")
@RequestMapping("api/attendee")
public class AttendeeController {
    private final IAttendeeService attendeeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
            content = @Content(schema = @Schema(implementation = AttendeeDTO.class)))
    })
    @Operation(
            summary = "Get Attendee Name by TrainingClass Id"
    )
    @GetMapping("ClassAttendee/{id}")
    public ResponseEntity<?> getAttendeeNameById (
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get attendee</b>",
                    example = "1") long id){
        try {
            return ResponseEntity.ok(attendeeService.getAttendeeById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any attendee with training class Id is " + id);
        }
    }
}
