package com.mockproject.controller;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RequiredArgsConstructor
public class DeliveryTypeController {




    private final IDeliveryTypeService deliveryTypeService;

    @GetMapping("")
    public ResponseEntity<List<DeliveryTypeDTO>> getAll(){
        List<DeliveryTypeDTO> deliveryTypeDTOList = deliveryTypeService.getDeliveryTypes(true);
        return ResponseEntity.ok(deliveryTypeDTOList);
    }

    @GetMapping("/{deliveryTypeId}")
    public ResponseEntity<DeliveryTypeDTO> getDeliveryTypeById(@PathVariable("deliveryTypeId") long id){
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeService.getDeliveryType(id, true);
        return ResponseEntity.ok(deliveryTypeDTO);
    }
}
