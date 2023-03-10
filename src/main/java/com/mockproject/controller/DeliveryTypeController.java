package com.mockproject.controller;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/delivery-type")
public class DeliveryTypeController {

    private final IDeliveryTypeService service;

    @ApiResponse(responseCode = "404", description = "When don't find any user")
    @Operation(summary = "Get Delivery Type by given ID")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@Parameter(description = "Delivery Type ID") @PathVariable("id") Long id){
        DeliveryTypeDTO deliveryTypeDTO = service.getByIdTrue(id);
        if (deliveryTypeDTO!=null){
            return ResponseEntity.ok(deliveryTypeDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Delivery Type have ID = " + id);
        }
    }

}
