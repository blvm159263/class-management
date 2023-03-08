package com.mockproject.controller;

import com.mockproject.service.interfaces.ISyllabusService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/syllabus")
public class SyllabusController {

    private final ISyllabusService syllabusService;

    @GetMapping("/list")
    public ResponseEntity<?> getListSyllabus(
            @RequestParam(defaultValue = "2000-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(defaultValue = "")String search,
            @RequestParam(defaultValue = "0") Optional<Integer> page,
            @RequestParam(defaultValue = "dateCreated,asc") String[] sort)
    {
        return ResponseEntity.ok(syllabusService.getListSyllabus(true,  fromDate, toDate, search, sort, page));
    }

}
