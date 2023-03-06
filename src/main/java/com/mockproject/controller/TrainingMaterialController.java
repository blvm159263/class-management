package com.mockproject.controller;

import com.mockproject.entity.TrainingMaterial;
import com.mockproject.service.TrainingMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/training-material")
public class TrainingMaterialController {

    @Autowired
    TrainingMaterialService trainingMaterialService;

    @GetMapping("/{id}")
    public ResponseEntity<List<TrainingMaterial>> getAllTrainingMaterialByUnitDetailId(@PathVariable("id") long id){
        List<TrainingMaterial> listTrainingMaterial = trainingMaterialService.getAllTrainingMaterialByUnitDetailId(id, true);
        return ResponseEntity.ok(listTrainingMaterial);
    }
}
