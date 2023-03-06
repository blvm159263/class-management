package com.mockproject.controller;

import com.mockproject.entity.UnitDetail;
import com.mockproject.service.UnitDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
