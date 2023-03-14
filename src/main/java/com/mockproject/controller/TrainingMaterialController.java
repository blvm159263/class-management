package com.mockproject.controller;

import com.mockproject.dto.CreateTrainingMaterialDTO;
import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.zip.DataFormatException;


import com.mockproject.service.TrainingMaterialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/training-material")
@AllArgsConstructor
public class TrainingMaterialController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    @Autowired
    TrainingMaterialService trainingMaterialService;

    @Autowired
    UserRepository userRepository;



    @GetMapping("get-all/{id}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<TrainingMaterialDTO>> getFiles(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(trainingMaterialService.getFiles(unitDetailId, true));
    }


    @PostMapping("/upload-file/{unitDetailID}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<List<TrainingMaterialDTO>> uploadFile(@PathVariable("unitDetailID") long unitDetailID,@RequestBody List<CreateTrainingMaterialDTO> createTrainingMaterialDTO) throws IOException{
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return ResponseEntity.ok(trainingMaterialService.uploadFile(createTrainingMaterialDTO,
                user.getUser(), unitDetailID));
    }

    @GetMapping("/get-file/{id}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<TrainingMaterialDTO> getFile(@PathVariable("id") long id) throws DataFormatException, IOException {
        TrainingMaterialDTO trainingMaterialDTO = trainingMaterialService.getFile(id, true);
        return ResponseEntity.ok()
               // .contentType(MediaType.valueOf(trainingMaterialDTO.getType()))
                .body(trainingMaterialDTO);
    }

    @PutMapping("/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<TrainingMaterialDTO> updateFile(
            @PathVariable("id") long id,
            @RequestParam(name = "file") CreateTrainingMaterialDTO dto
            ) throws IOException {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(trainingMaterialService.updateFile(id, dto, user.getUser(), true));
    }

    @PutMapping("/delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteFile(@PathVariable("id") long trainingMaterialId){
        return ResponseEntity.ok(trainingMaterialService.deleteTrainingMaterial(trainingMaterialId, true));
    }

    @PutMapping("/multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteFiles(@PathVariable("id") long unitDetailId){
        return ResponseEntity.ok(trainingMaterialService.deleteTrainingMaterials(unitDetailId, true));
    }
}

