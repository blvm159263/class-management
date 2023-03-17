package com.mockproject.controller;

import com.mockproject.service.UnitDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Unit Details API")
@RequiredArgsConstructor
@RequestMapping("api/unitDetails")
public class UnitDetailsController {
    private final UnitDetailService unitDetailService;
}
