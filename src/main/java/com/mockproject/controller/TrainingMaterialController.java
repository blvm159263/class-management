package com.mockproject.controller;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("api/training-material")
@RequiredArgsConstructor
public class TrainingMaterialController {
    @Autowired
    private final ITrainingMaterialService trainingMaterialService;

    @PostMapping("upload-file")
    public ResponseEntity<List<TrainingMaterialDTO>> uploadFile(
            @RequestParam(name = "file") MultipartFile[] files,
            @RequestParam(name = "unit_detail_id") long unitDetailId,
            @RequestParam(name = "user_id") long userId
    ) throws IOException{
        return ResponseEntity.ok(trainingMaterialService.uploadFile(files, unitDetailId, userId));
    }

    @GetMapping()
    public ResponseEntity<List<TrainingMaterialDTO>> getFiles(){
        return ResponseEntity.ok(trainingMaterialService.getFiles());
    }

    @GetMapping("{id}")
    public ResponseEntity<TrainingMaterialDTO> getFile(@PathVariable("id") long id) throws DataFormatException, IOException {
        return ResponseEntity.ok(trainingMaterialService.getFile(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TrainingMaterialDTO> updateFile(
            @PathVariable("id") long id,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "unit_detail_id") long unitDetailId,
            @RequestParam(name = "user_id") long userId) throws IOException {
        return ResponseEntity.ok(trainingMaterialService.updateFile(id, file, unitDetailId, userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TrainingMaterialDTO> deleteFile(
            @PathVariable("id") long id
    ){
        return ResponseEntity.ok(trainingMaterialService.deleteFile(id));
    }
}