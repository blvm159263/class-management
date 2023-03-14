package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassService;
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
@Tag(name = "Training Class API")
@RequiredArgsConstructor
@RequestMapping("api/trainingClass")
public class TrainingClassController {
    private final ITrainingClassService trainingClassService;

    @Operation(
            summary = "Get all the data of the training class"
    )
    @GetMapping("{classCode}")
    public ResponseEntity<?> getTrainingClassByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code){
        return  ResponseEntity.ok(trainingClassService.getTrainingClassByClassCode(code));
    }
}
