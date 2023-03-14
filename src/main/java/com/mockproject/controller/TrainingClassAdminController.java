package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassAdminService;
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
@Tag(name = "Training Class Admin API")
@RequiredArgsConstructor
@RequestMapping("api/trainingClassAdmin")
public class TrainingClassAdminController {

    private final ITrainingClassAdminService trainingClassAdminService;
    @Operation(
            summary = "Get all the admin of the training class by class code",
            description = "<b>List all the admin of the training class</b>"
    )
    @GetMapping("adminTrainingClass/{classCode}")
    public ResponseEntity<?> getAdminByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(trainingClassAdminService.getAdminByClassCode(code));
    }
}
