package com.mockproject.controller;

import com.mockproject.service.interfaces.ITowerService;
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
@Tag(name = "Tower API")
@RequiredArgsConstructor
@RequestMapping("api/tower")
public class TowerController {

    private final ITowerService towerService;

    @Operation(
            summary = "Get all the tower that the training class studies by class code",
            description = "<b>List all the tower that the training class will study<b>"
    )
    @GetMapping("trainingClass/{classCode}")
    public ResponseEntity<?> getTowerByClassCode(
            @PathVariable ("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(towerService.getTowerByClassCode(code));
    }

    @Operation(
            summary = "Get the tower that the training class studies on that day",
            description = "<b>List the information of the tower that the training class will study<b>"
    )
    @GetMapping("trainingClass/tower-on-date")
    public ResponseEntity<?> getTowerOnTheDay(
            @Parameter(
                    description = "<b>Insert Training Class Id</b>",
                    example = "1"
            )
            long id,
            @Parameter(
                    description = "<b>Insert id of the day</b>",
                    example = "1"
            )
            int day
            ) {
        return ResponseEntity.ok(towerService.getTowerForTheDay(id, day));
    }
}