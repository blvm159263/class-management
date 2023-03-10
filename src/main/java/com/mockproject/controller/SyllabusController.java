package com.mockproject.controller;

import com.mockproject.service.interfaces.ISyllabusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Authorization")
public class SyllabusController {
    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

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
