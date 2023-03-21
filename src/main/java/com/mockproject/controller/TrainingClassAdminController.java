package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.ITrainingClassAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Training Class Admin API")
@RequestMapping("api/training-class-admin")
@SecurityRequirement(name = "Authorization")
public class TrainingClassAdminController {

    private final ITrainingClassAdminService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When list saved successful!"),
            @ApiResponse(responseCode = "400", description = "When saving fail!")
    })
    @Operation(summary = "Save list of class admin by given Training Class Id and List ID of admin")
    @PostMapping("training-class/{tcId}")
    public ResponseEntity<?> createListClassAdmin(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                            content = @Content(examples = @ExampleObject(value = "[1, 2, 3]") ))
                                                  @RequestBody List<Long> listAdminId,
                                                  @Parameter(description = "Training Class ID when call create Training Class API return")
                                                  @PathVariable("tcId") Long tcId){
        if(service.saveList(listAdminId, tcId)){
            return new ResponseEntity<>("List Class Admin have been saved successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Saving fail!", HttpStatus.BAD_REQUEST);
    }

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
            return ResponseEntity.ok(service.getAdminByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any admin with training class Id is " + id);
        }
    }
}
