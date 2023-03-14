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
@RequestMapping("api/attendee")
public class AttendeeController {
    private final IAttendeeService attendeeService;

    @Operation(
            summary = "Get Attendee Name by TrainingClass Code"
    )
    @GetMapping("ClassAttendee/{classCode}")
    public ResponseEntity<?> getAttendeeNameByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Class Code</b>",
                    example = "DN22_IN_FT_02") String code) {
        return ResponseEntity.ok(attendeeService.getAttendeeByClassCode(code));
    }
}
