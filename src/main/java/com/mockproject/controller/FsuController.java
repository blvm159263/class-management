package com.mockproject.controller;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.FsuDTO;
import com.mockproject.service.interfaces.IFsuService;
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
@Tag(name = "FSU API")
@RequiredArgsConstructor
@RequestMapping("api/Fsu")
public class FsuController {
    private final IFsuService fsuService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = FsuDTO.class)))
    })
    @Operation(
            summary = "Get the FSU of the training class"
    )
    @GetMapping("trainingClass/{id}")
    public ResponseEntity<?> getFsuByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get the FSU</b>",
                    example = "1"
            ) long id) {
        try {
            return ResponseEntity.ok(fsuService.getFsuByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any FSU with training class Id is " + id);
        }

    }
}
