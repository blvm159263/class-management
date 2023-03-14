package com.mockproject.controller;

import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Delivery Type API")
@RequiredArgsConstructor
@RequestMapping("api/deliveryType")
public class DeliveryTypeController {
    private final IDeliveryTypeService deliveryTypeService;

    @Operation(
            summary = "Get all the delivery type of the training program by class code",
            description = "<b>List all the delivery type of the training program</b>"
    )
    @GetMapping("trainingProgram/{classCode}")
    public ResponseEntity<?> getDeliveryTypeByClassCode(
            @RequestParam(defaultValue = "CT22_FR_OOP_01")
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Class Code</b>") String code) {
        return ResponseEntity.ok(deliveryTypeService.getListDeliveryTpeByCLassCode(code));
    }
}
