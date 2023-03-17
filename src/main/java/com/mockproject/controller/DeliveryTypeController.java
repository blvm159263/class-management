package com.mockproject.controller;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@RestController
@Tag(name = "Delivery API")
@RequestMapping(value = "/api/delivery")
@Tag(name = "Delivery type", description = "API related delivery type")
@RequiredArgsConstructor
public class DeliveryTypeController {

    private final IDeliveryTypeService deliveryTypeService;

    @GetMapping("")
    @Operation(summary = "Get all delivery type")
    public ResponseEntity<List<DeliveryTypeDTO>> getAll(){
        List<DeliveryTypeDTO> deliveryTypeDTOList = deliveryTypeService.getDeliveryTypes(true);
        return ResponseEntity.ok(deliveryTypeDTOList);
    }

    @GetMapping("/{deliveryTypeId}")
    @Operation(summary = "get Delivery type by delivery type id")
    public ResponseEntity<DeliveryTypeDTO> getDeliveryTypeById(@PathVariable("deliveryTypeId") long id){
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeService.getDeliveryType(id, true);
        return ResponseEntity.ok(deliveryTypeDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any delivery type"),
            @ApiResponse(responseCode = "200", description = "When found delivery type",
            content = @Content(schema = @Schema(implementation = DeliveryTypeDTO.class)))
    })
    @Operation(summary = "Get Delivery Type by given ID")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@Parameter(description = "Delivery Type ID") @PathVariable("id") Long id) {
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeService.getByIdTrue(id);
        if (deliveryTypeDTO != null) {
            return ResponseEntity.ok(deliveryTypeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Delivery Type have ID = " + id);
        }
    }

}
