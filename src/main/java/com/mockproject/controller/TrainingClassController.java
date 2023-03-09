package com.mockproject.controller;

import com.mockproject.service.interfaces.IAttendeeService;
import com.mockproject.service.interfaces.IFsuService;
import com.mockproject.service.interfaces.ILocationService;
import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            @RequestParam(defaultValue = "2000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(100)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(defaultValue = "0,1,2") List<Integer> period,
            @RequestParam(defaultValue = "false") boolean isOnline,
            @RequestParam(defaultValue = "") String state,
            @RequestParam(defaultValue = "") List<Long> attendee,
            @RequestParam(defaultValue = "") String fsu,
            @RequestParam(defaultValue = "") String trainerId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "startTime,asc") String[] sort,
            @RequestParam(defaultValue = "0") Optional<Integer> page)
    {
        return ResponseEntity
                .ok(classService.getListClass(true,
                        location.isEmpty() ? locationService.getAllLocation().stream().map(l -> l.getId()).collect(Collectors.toList()) : location,
                        fromDate, toDate, period, isOnline? "Online" : "", state,
                        attendee.isEmpty() ? attendeeService.getAllAttendee().stream().map(a -> a.getId()).collect(Collectors.toList()) : attendee,
                        StringUtils.isNumeric(fsu) ? fsuService.getFsuById(Long.parseLong(fsu)).getFsuName() : "",
                        StringUtils.isNumeric(trainerId) ? Long.parseLong(trainerId) : 0,
                        search, sort, page));
    }
}
