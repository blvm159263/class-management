package com.mockproject.controller;

import com.mockproject.service.interfaces.IAttendeeService;
import com.mockproject.service.interfaces.IFsuService;
import com.mockproject.service.interfaces.ILocationService;
import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/class")
public class TrainingClassController {

    private final ITrainingClassService classService;

    private final ILocationService locationService;

    private final IAttendeeService attendeeService;

    private final IFsuService fsuService;

    @GetMapping("/list")
    public ResponseEntity<?> getListClass(
            @RequestParam(defaultValue = "") List<Long> location,
            @RequestParam(defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(defaultValue = "") List<Integer> period,
            @RequestParam(defaultValue = "false") boolean isOnline,
            @RequestParam(defaultValue = "") String state,
            @RequestParam(defaultValue = "") List<Long> attendee,
            @RequestParam(defaultValue = "0") long fsu,
            @RequestParam(defaultValue = "0") long trainerId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "startTime,asc") String[] sort,
            @RequestParam(defaultValue = "0") Optional<Integer> page)
    {
        return ResponseEntity
                .ok(classService.getListClass(true, location, fromDate, toDate, period,
                        isOnline? "Online" : "", state, attendee, fsu, trainerId, search, sort, page));
    }
}
