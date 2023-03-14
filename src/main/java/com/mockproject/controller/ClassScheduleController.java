package com.mockproject.controller;

import com.mockproject.service.interfaces.IClassScheduleService;
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
@Tag(name = "Class Schedule API")
@RequiredArgsConstructor
@RequestMapping("api/classSchedule")
public class ClassScheduleController {
    private final IClassScheduleService classScheduleService;

    @Operation(
            summary = "Get all schedule of the training class by class code",
            description = "<b>List all schedule of the training class</b>"
    )
    @GetMapping("trainingclassSchedule/{classCode}")
    public ResponseEntity<?> getScheduleByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02") String code) {
        return ResponseEntity.ok(classScheduleService.getScheduleByClassCode(code));
    }
}
