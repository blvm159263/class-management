package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.service.interfaces.ISyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Syllabus API")
@RequestMapping("api/syllabus")
public class SyllabusController {

    private final ISyllabusService syllabusService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any syllabus"),
            @ApiResponse(responseCode = "200", description = "When found list of syllabus",
                    content = @Content(schema = @Schema(implementation = SyllabusDTO.class)))
    })
    @Operation(summary = "Get all Syllabus by given Training Program ID")
    @GetMapping("/list-by-training-program/{id}")
    public ResponseEntity<?> listSyllabusByTrainingProgramId(@Parameter(description = "Training Class's ID that want to get Syllabus")
                                                             @PathVariable("id") Long id) {
        List<SyllabusDTO> list = syllabusService.listByTrainingProgramIdTrue(id);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any syllabus with Training Program = " + id);
        }
    }

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
                    example = "C#"
            )
            String search,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>Insert page number (0 => first page)<b>",
                    example = "0"
            )
            Optional<Integer> page,

            @RequestParam(defaultValue = "dateCreated,asc")
            @Parameter(
                    description = "<b>Sort by attribute descending/ascending (dateCreated,asc => sort by dateCreated ascending)<b>",
                    example = "dateCreated,asc"
            )
            String[] sort) {
        return ResponseEntity
                .ok(syllabusService.getListSyllabus(true,  fromDate, toDate, search, sort, page));
    }

}
