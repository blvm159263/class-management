package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/syllabuses")
@RequiredArgsConstructor
public class SyllabusController {
    private final SyllabusService syllabusService;

    @GetMapping
    public ResponseEntity<List<SyllabusDTO>> getAll(){
        return ResponseEntity.ok(syllabusService.getAll());
    }
}
