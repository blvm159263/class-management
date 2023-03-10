package com.mockproject.controller;

import com.mockproject.dto.FsuDTO;
import com.mockproject.service.interfaces.IFsuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Fsu")
public class FsuController {
    private final IFsuService fsuService;

    @GetMapping("{classCode}")
    public FsuDTO getFsuByClassCode(@PathVariable("classCode") String code) {
        return fsuService.getFsuByClassCode(code);
    }
}
