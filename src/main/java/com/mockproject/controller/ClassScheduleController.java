package com.mockproject.controller;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Class Schedule API")
@RequiredArgsConstructor
@RequestMapping("api/classSchedule")
public class ClassScheduleController {
    private final IClassScheduleService classScheduleService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = ClassScheduleDTO.class)))
    })
    @Operation(
            summary = "Get all schedule of the training class by training class ID",
            description = "<b>List all schedule of the training class</b>"
    )
    @GetMapping("trainingclassSchedule/{id}")
    public ResponseEntity<?> getScheduleyId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get schedule</b>",
                    example = "1") long id) {
        try {
            return ResponseEntity.ok(classScheduleService.getScheduleById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any schedule with training class Id is " + id);
        }

    }
}
