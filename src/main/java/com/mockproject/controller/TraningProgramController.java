package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingProgramService;
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
@Tag(name = "Training Program API")
@RequiredArgsConstructor
@RequestMapping("api/trainingPropram")
public class TraningProgramController {
    private final ITrainingProgramService trainingProgramService;

    @Operation(
            summary = "Get the training program that the training class studies",
            description = "<b>List the data of the training program that the training class studies</b>"
    )
    @GetMapping("trainingClass/{classCode}")
    public ResponseEntity<?> getTraningProgramByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(trainingProgramService.getTraningProgramByClassCode(code));
    }
}
