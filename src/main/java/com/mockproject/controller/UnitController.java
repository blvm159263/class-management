package com.mockproject.controller;

import com.mockproject.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Unit API")
@RequiredArgsConstructor
@RequestMapping("api/unit")
public class UnitController {
    private final UnitService unitService;

    @Operation(
            summary = "Get the unit that the training class studies on that day",
            description = "<b>List the information of the unit that the training class studies on that day"
    )
    @GetMapping("trainingClassStudies")
    public ResponseEntity<?> getUnitFromTheDate(
            @Parameter(
                    description = "<b>Insert Training Class Id</b>"
            ) long id,
            @Parameter(
                    description = "<b>Insert the id of the day to get the unit of the Training Class</b>"
            ) int day) {
        return ResponseEntity.ok(unitService.getAllUnitsForTheDate(id, day));
    }

}
