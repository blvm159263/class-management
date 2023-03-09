package com.mockproject.controller;

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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/class")
public class TrainingClassController {

    private final ITrainingClassService classService;

    private final ILocationService locationService;

    private final IFsuService fsuService;

    @GetMapping("/list")
    public ResponseEntity<?> getListClass(
            @RequestParam(defaultValue = "") List<Long> location,
            @RequestParam(defaultValue = "2000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(100)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(defaultValue = "0,1,2") List<Integer> period,
            @RequestParam(defaultValue = "false") boolean isOnline,
            @RequestParam(defaultValue = "Planning,Openning,Closed") String state,
            @RequestParam(defaultValue = "") List<Long> attendee,
            @RequestParam(defaultValue = "0") long fsu)
    {
        if(location.isEmpty()) location = locationService.getAllLocation().stream().map(l -> l.getId()).collect(Collectors.toList());

        return null;
    }
}
