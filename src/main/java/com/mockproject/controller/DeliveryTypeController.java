package com.mockproject.controller;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deliveryType")
public class DeliveryTypeController {
    private final IDeliveryTypeService deliveryTypeService;

    @GetMapping("trainingProgram/{classCode}")
    public List<DeliveryTypeDTO> getDeliveryTypeByClassCode(@PathVariable("classCode") String code) {
        return deliveryTypeService.getListDeliveryTpeByCLassCode(code);
    }
}
