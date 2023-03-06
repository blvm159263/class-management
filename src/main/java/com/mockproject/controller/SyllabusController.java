package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import com.mockproject.service.SyllabusService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
        List<Syllabus> listSyllabus = syllabusService.getAll();
        return ResponseEntity.ok(listSyllabus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Syllabus> getSyllabus(@PathVariable("id") long id){
        Syllabus syllabus = syllabusService.getSyllabus(id);
        return ResponseEntity.ok(syllabus);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        long syllabusID = syllabusService.create(syllabus);
        return ResponseEntity.ok(syllabusID);
    }
}
