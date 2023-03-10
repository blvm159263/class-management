package com.mockproject.controller;

import com.mockproject.dto.TowerDTO;
import com.mockproject.service.interfaces.ITowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tower")
public class TowerController {

    private final ITowerService towerService;

    @GetMapping("traningClass/{classCode}")
    public List<TowerDTO> getTowerByClassCode(@PathVariable ("classCode") String code, boolean status) {
        return towerService.getTowerByClassCode(code, status);
    }
}
