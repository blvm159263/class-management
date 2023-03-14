package com.mockproject.controller;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Training Class API")
@RequestMapping("api/training-class")
public class TrainingClassController {

    private final ITrainingClassService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When Training Class created successfully!"),
            @ApiResponse(responseCode = "400", description = "When Training Class can't be created - Object is not valid!")
    })
    @Operation(summary = "Create new Training Class")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TrainingClassDTO dto){
        Long id = service.create(dto);
        if(id!=null){
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Can't not create Training Class", HttpStatus.BAD_REQUEST);
        }
    }

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
                    description = "<b>Class time:<b>"
                            + "<ul><li>0: Morning</li>"
                            + "<li>1: Noon</li>"
                            + "<li>2: Night</li></ul>",
                    example = "0"
            ) List<Integer> period,

            @RequestParam(defaultValue = "false")
            @Parameter(
                    description = "<b>Class time<b>"
                            + "<ul><li>true : Online</li>"
                            + "<li>false : No filter</li>",
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
