package com.mockproject.controller;

import com.mockproject.service.interfaces.ISyllabusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/syllabus")
public class SyllabusController {

    private final ISyllabusService service;

    @GetMapping("/list-by-training-program")
    public ResponseEntity<?> listSyllabusByTrainingProgramId(@RequestParam long id){
        return ResponseEntity.ok(service.listByTrainingProgramIdTrue(id));
    }
}
