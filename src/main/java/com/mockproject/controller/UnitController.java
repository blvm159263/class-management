package com.mockproject.controller;

import com.mockproject.entity.Unit;
import com.mockproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
