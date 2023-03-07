package com.mockproject.controller;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;
import com.mockproject.service.UnitDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(unitDetailService.createUnitDetail(unitId,listUnitDetail));
    }

}
