package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassService;
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
@Tag(name = "Training Class API")
@RequestMapping("/api/class")
public class TrainingClassController {  

    private final ITrainingClassService classService;

    @GetMapping("/list")
    @Operation(
            summary = "Get training class list",
            description = "<b>List of training class according to search, sort, filter, and pages<b>"
    )
    public ResponseEntity<?> getListClass(
            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Filter by location ID<b>",
                    example = "1"
            ) List<Long> location,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Class time frame - Start day (yyyy-mm-dd)<b>",
                    example = "2000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Class time frame - End day (yyyy-mm-dd)<b>",
                    example = "3000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Class time:"
                            + "<ul><li>0: Morning</li>"
                            + "<li>1: Noon</li>"
                            + "<li>2: Night</li></ul><b>",
                    example = "0"
            ) List<Integer> period,

            @RequestParam(defaultValue = "false")
            @Parameter(
                    description = "<b>Class time - true: Online, false: No filter<b>",
                    example = "false"
            ) boolean isOnline,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Status:"
                            + "<ul><li>Planning</li>"
                            + "<li>Openning</li>"
                            + "<li>Closed</li></u><b>",
                    example = "Planning"
            ) String state,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Attendee - Filter by attendee ID<b>",
                    example = "1"
            ) List<Long> attendee,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>FSU - Filter by FSU ID<b>",
                    example = "1"
            ) long fsu,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>Trainer - Filter by trainer ID<b>",
                    example = "0"
            ) long trainerId,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Search by class name, code, or creator's name<b>",
                    example = "Fresher Develop Operation"
            ) String search,

            @RequestParam(defaultValue = "startTime,asc")
            @Parameter(
                    description = "<b>Sort by attribute descending/ascending (startTime,asc => sort by startTime ascending)<b>",
                    example = "startTime,asc"
            ) String[] sort,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>Insert page number (0 => first page)<b>",
                    example = "0"
            ) Optional<Integer> page)
    {
        return ResponseEntity
                .ok(classService.getListClass(true, location, fromDate, toDate, period,
                        isOnline? "Online" : "", state, attendee, fsu, trainerId, search, sort, page));
    }
}
