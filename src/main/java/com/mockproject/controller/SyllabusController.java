package com.mockproject.controller;

import com.mockproject.service.interfaces.ISyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Syllabus API")
@RequestMapping("/api/syllabus")
public class SyllabusController {

    private final ISyllabusService syllabusService;

    @GetMapping("/list")
    @Operation(
            summary = "Get syllabus list",
            description = "<b>List of syllabus according to search, sort, filter, and pages<b>"
    )
    public ResponseEntity<?> getListSyllabus(
            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Created date - Start day (yyyy-mm-dd)<b>",
                    example = "2000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Created date - End day (yyyy-mm-dd)<b>",
                    example = "3000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Search by syllabus name, code, creator's name, or output standard<b>",
                    example = ""
            )
            List<String> search,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>Insert page number (0 => first page)<b>",
                    example = "0"
            )
            Optional<Integer> page,

            @RequestParam(defaultValue = "name,asc")
            @Parameter(
                    description = "<b>Sort by attribute descending/ascending"
                            + "<li>dateCreated,asc => sort by dateCreated ascending</li>"
                            + "<li>creator,desc => sort by creator's name descending</li></u><b>",
                    example = "name,asc"
            )
            String[] sort) {
        return ResponseEntity
                .ok(syllabusService.getListSyllabus(true,  fromDate, toDate, search, sort, page));
    }

}
