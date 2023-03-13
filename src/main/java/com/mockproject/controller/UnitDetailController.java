package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import com.mockproject.service.UnitDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unit-detail")
public class UnitDetailController {

    @Autowired
    public UnitDetailService unitDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<List<UnitDetail>> getAllUnitDetailByUnitId(@PathVariable("id") long unitId){
        List<UnitDetail> listUnitDetail = unitDetailService.getAllUnitDetailByUnitId(unitId, true);
        return ResponseEntity.ok(listUnitDetail);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Boolean> createUnitDetail(@PathVariable("id") long unitId, @RequestBody List<UnitDetailDTO> listUnitDetail){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitDetailService.createUnitDetail(unitId,listUnitDetail, user.getUser()));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UnitDetail> editUnitDetail(@PathVariable("id") long id, @RequestBody UnitDetailDTO unitDetailDTO){
        UnitDetail updateUnitDetail = unitDetailService.editUnitDetail(id, unitDetailDTO, true);
        return ResponseEntity.ok(updateUnitDetail);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUnitDetail(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(unitDetailService.deleteUnitDetail(unitDetailId, true));
    }

    @PutMapping("/multi-delete/{id}")
    public ResponseEntity<Boolean> deleteUnitDetails(@PathVariable("id") long unitId){
        return ResponseEntity.ok(unitDetailService.deleteUnitDetails(unitId, true));
    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<Boolean> toggleUnitDetailType(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(unitDetailService.toggleUnitDetailType(unitDetailId, true));
    }
}
