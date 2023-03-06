package com.mockproject.controller;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("api/training-material")
@RequiredArgsConstructor
public class TrainingMaterialController {
    @Autowired
    private final ITrainingMaterialService trainingMaterialService;
    @PostMapping("upload-file")
    public ResponseEntity<TrainingMaterialDTO> uploadFile(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "unit_detail_id") long unitDetailId,
            @RequestParam(name = "user_id") long userId
            ) throws IOException {
        return ResponseEntity.ok(trainingMaterialService.uploadFile(file, unitDetailId, userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") long id) throws DataFormatException, IOException {
        TrainingMaterialDTO trainingMaterialDTO = trainingMaterialService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(trainingMaterialDTO.getType()))
                .body(trainingMaterialDTO.getData());
    }
}