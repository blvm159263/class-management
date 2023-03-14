package com.mockproject.controller;

import com.mockproject.service.interfaces.IFsuService;
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
@Tag(name = "FSU API")
@RequiredArgsConstructor
@RequestMapping("api/Fsu")
public class FsuController {
    private final IFsuService fsuService;

    @Operation(
            summary = "Get the FSU of the training class"
    )
    @GetMapping("trainingClass/{classCode}")
    public ResponseEntity<?> getFsuByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            ) String code) {
        return ResponseEntity.ok(fsuService.getFsuByClassCode(code));
    }
}
