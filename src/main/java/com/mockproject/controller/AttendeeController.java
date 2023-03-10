package com.mockproject.controller;


import com.mockproject.dto.AttendeeDTO;
import com.mockproject.service.interfaces.IAttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/attendee")
public class AttendeeController {
    private final IAttendeeService attendeeService;

    @GetMapping("ClassAttendee/{classCode}")
    public AttendeeDTO getAttendeeNameByClassCode(@PathVariable("classCode") String code) {
        return attendeeService.getAttendeeByClassCode(code);
    }
}
