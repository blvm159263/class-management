package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitService.createUnit(sessionId, listUnit, user.getUser().getId()));
    }

    @PutMapping("/edit/{sessionId}/{id}")
    public ResponseEntity<Unit> editUnit(@PathVariable("id") long id, @PathVariable("sessionId") long sessionId, @RequestBody UnitDTO unitDTO){
        Unit updateUnit = unitService.editUnit(id, unitDTO, true);
        return ResponseEntity.ok(updateUnit);
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteUnit(@PathVariable("id") long unitId){
        return ResponseEntity.ok(unitService.deleteUnit(unitId, true));
    }

    @PutMapping("multi-delete/{id}")
    public ResponseEntity<Boolean> deleteUnits(@PathVariable("id") long sessionId){
        return ResponseEntity.ok(unitService.deleteUnits(sessionId, true));
    }
}
