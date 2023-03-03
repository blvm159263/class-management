package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import com.mockproject.service.SyllabusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/syllabus")
public class SyllabusController {

    @Autowired
    public SyllabusService syllabusService;

    @GetMapping
    public ResponseEntity<List<Syllabus>> getAll(){
        return ResponseEntity.ok(syllabusService.getAll());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        User user = new User();
        user.setId(3);
        long syllabusID = syllabusService.create(syllabus, user);
        return ResponseEntity.ok(syllabusID);
    }
}
