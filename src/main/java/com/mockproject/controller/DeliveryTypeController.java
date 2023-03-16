package com.mockproject.controller;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
}
