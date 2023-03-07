package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import com.mockproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unit")
public class UnitController {

    @Autowired
    public UnitService unitService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Unit>> getAllUnitBySessionId(@PathVariable("id") long sessionId){
        List<Unit> listUnit = unitService.getAllUnitBySessionId(sessionId, true);
        return ResponseEntity.ok(listUnit);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Boolean> createUnit(@PathVariable("id") long sessionId,@RequestBody List<UnitDTO> listUnit){
        return ResponseEntity.ok(unitService.createUnit(sessionId, listUnit));
    }
}
