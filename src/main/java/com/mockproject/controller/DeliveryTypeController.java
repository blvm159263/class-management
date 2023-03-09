package com.mockproject.controller;

import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Get Delivery Type by given ID")
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@Parameter(description = "Delivery Type ID") @PathVariable("id") long id){
        return ResponseEntity.ok(service.getByIdTrue(id));
    }

}
