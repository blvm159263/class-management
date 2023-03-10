package com.mockproject.controller;

import com.mockproject.dto.TrainingMaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;


import com.mockproject.entity.TrainingMaterial;
import com.mockproject.service.TrainingMaterialService;
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

    @GetMapping("get-all/{id}")
    public ResponseEntity<List<TrainingMaterialDTO>> getFiles(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(trainingMaterialService.getFiles(unitDetailId, true));
    }


    @PostMapping("/upload-file")
    public ResponseEntity<List<TrainingMaterialDTO>> uploadFile(
            @RequestParam(name = "file") MultipartFile[] files,
            @RequestParam(name = "unit_detail_id") long unitDetailId,
            @RequestParam(name = "user_id") long userId
    ) throws IOException{
        return ResponseEntity.ok(trainingMaterialService.uploadFile(files, unitDetailId, userId));
    }

    @GetMapping("/get-file/{id}")
    public ResponseEntity<TrainingMaterialDTO> getFile(@PathVariable("id") long id) throws DataFormatException, IOException {
        TrainingMaterialDTO trainingMaterialDTO = trainingMaterialService.getFile(id, true);
        return ResponseEntity.ok()
               // .contentType(MediaType.valueOf(trainingMaterialDTO.getType()))
                .body(trainingMaterialDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingMaterialDTO> updateFile(
            @PathVariable("id") long id,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "unit_detail_id") long unitDetailId,
            @RequestParam(name = "user_id") long userId) throws IOException {
        return ResponseEntity.ok(trainingMaterialService.updateFile(id, file, unitDetailId, userId, true));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteFile(@PathVariable("id") long trainingMaterialId){
        return ResponseEntity.ok(trainingMaterialService.deleteTrainingMaterial(trainingMaterialId, true));
    }

    @PutMapping("/multi-delete/{id}")
    public ResponseEntity<Boolean> deleteFiles(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(trainingMaterialService.deleteTrainingMaterials(unitDetailId, true));
    }
}

