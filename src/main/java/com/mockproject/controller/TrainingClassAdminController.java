package com.mockproject.controller;

import com.mockproject.dto.TowerDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.ITrainingClassAdminService;
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
@Tag(name = "Training Class Admin API")
@RequiredArgsConstructor
@RequestMapping("api/trainingClassAdmin")
public class TrainingClassAdminController {

    private final ITrainingClassAdminService trainingClassAdminService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(
            summary = "Get all the admin of the training class by training class ID",
            description = "<b>List all the admin of the training class</b>"
    )
    @GetMapping("adminTrainingClass/{id}")
    public ResponseEntity<?> getAdminByClassId(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID</b>",
                    example = "1"
            )
            long id) {
        try {
            return ResponseEntity.ok(trainingClassAdminService.getAdminByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any admin with training class Id is " + id);
        }
    }
}
