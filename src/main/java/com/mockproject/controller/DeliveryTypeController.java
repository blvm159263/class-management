package com.mockproject.controller;

import com.mockproject.dto.ContactDTO;
import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Delivery Type API")
@RequiredArgsConstructor
@RequestMapping("api/deliveryType")
public class DeliveryTypeController {
    private final IDeliveryTypeService deliveryTypeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = DeliveryTypeDTO.class)))
    })
    @Operation(
            summary = "Get all the delivery type of the training program by Id",
            description = "<b>List all the delivery type of the training program</b>"
    )
    @GetMapping("trainingProgram/{id}")
    public ResponseEntity<?> getDeliveryTypeById(
            @RequestParam(defaultValue = "1")
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert Training Class ID to get the training program</b>") long id) {
        try {
            return ResponseEntity.ok(deliveryTypeService.getListDeliveryTypeByClassId(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't found training class with Id " + id);
        }

    }
}
