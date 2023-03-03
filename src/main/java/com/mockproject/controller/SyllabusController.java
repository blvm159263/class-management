package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.service.SyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusController {

    @Autowired
    public SyllabusService syllabusService;

    @GetMapping
    public ResponseEntity<List<Syllabus>> getAll(){
        return ResponseEntity.ok(syllabusService.getAll());
    }

}
